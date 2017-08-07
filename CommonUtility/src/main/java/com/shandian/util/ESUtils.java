package com.shandian.util;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shandian.CommonUtility.vo.InitConfigByCreateIndex;

public class ESUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Client clients = getClient();

    /**
     * 关闭对应client
     *
     * @param client
     */
    public static void close(Client client) {
        if (client != null) {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized Client getClient() {
        if (clients != null) {
            return clients;
        }
        clients = newClient();
        return clients;
    }

    /**
     * 根据默认系统默认配置初始化库
     *
     * @return
     */
    public static Client newClient() {
        // C:集群嗅探模式 部署Elasticsearch的cluster.name必须一样
        Map<String, String> map = new HashMap<String, String>();
        // 设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，
        // 把集群中其它机器的ip地址加到客户端中，这样本地节点挂了，程序还可以正常工作
        Settings settings = Settings.settingsBuilder().put(map).put("cluster.name", "?????")
                    .put("client.transport.sniff", true).build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        String host = "";
        int port = 8080;
        if (org.apache.commons.lang.StringUtils.isNotBlank(host)) {
            String[] hostArr = host.split(",");
            try {
                for (String hostIp : hostArr) {
                    client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostIp), port));
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    /**
     * 判断index是否存在
     *
     * @param client
     * @param indexName
     * @return
     */
    public static boolean indexExists(Client client, String indexName) {
        IndicesExistsRequest indicesExistsRequest = new IndicesExistsRequest();
        indicesExistsRequest.indices(new String[]{indexName.toLowerCase()});
        return client.admin().indices().exists(indicesExistsRequest).actionGet().isExists();
    }

    /**
     * 判断typeName是否存在
     *
     * @param client
     * @param indexName
     * @param indexType
     * @return
     */
    public static boolean typeExists(Client client, String indexName, String indexType) {
        if (indexExists(client, indexName)) {
            TypesExistsRequest typesExistsRequest = new TypesExistsRequest(new String[]{indexName.toLowerCase()},
                        indexType);
            return client.admin().indices().typesExists(typesExistsRequest).actionGet().isExists();
        }
        return false;
    }

    /**
     * 某个类初始化动作,String类型强制不分词
     *
     * @param client
     * @param initConfigByCreateIndex 具体看类字段注释
     * @param object
     * @return
     * @throws Exception
     */
    public static boolean initCreateIndex(Client client, InitConfigByCreateIndex initConfigByCreateIndex, Object object)
                throws Exception {
        String indexName = initConfigByCreateIndex.getIndexName();
        String typeName = object.getClass().getSimpleName();
        boolean indexExists = indexExists(client, indexName);
        if (!indexExists) {
            client.admin().indices().prepareCreate(indexName).execute().actionGet();
        }
        boolean typeExists = typeExists(client, indexName, typeName);
        if (typeExists) {
            return true;
        }
        XContentBuilder mapping = jsonBuilder().startObject().startObject(typeName);
        if (initConfigByCreateIndex.getParentStatus()) {
            mapping.startObject("_parent");
            mapping.field("type", initConfigByCreateIndex.getParentName());
            mapping.endObject();
        }
        mapping.startObject("properties");
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            Type type = field.getGenericType();
            if (type instanceof Class<?>) {
                Class<?> cls = (Class<?>) type;
                if (String.class.isAssignableFrom(cls)) {
                    mapping.startObject(field.getName()).field("type", "string").field("index", "not_analyzed")
                                .field("include_in_all", false).endObject();
                }
            }
        }
        mapping.endObject().endObject().endObject();
        System.out.println(mapping.string());
        PutMappingRequest mappingRequest = Requests.putMappingRequest(indexName).type(typeName).source(mapping);
        PutMappingResponse response = client.admin().indices().putMapping(mappingRequest).actionGet();
        return response.isAcknowledged();
    }

    /**
     * 保存或者修改索引
     *
     * @param client
     * @param objectList
     * @param initConfigByCreateIndex 具体看类字段注释
     */
    public static void saveOrUpdate(Client client, InitConfigByCreateIndex initConfigByCreateIndex,
                List<Object> objectList) {
        try {
            if (objectList != null && !objectList.isEmpty()) {
                String indexName = initConfigByCreateIndex.getIndexName();
                String fieldName = initConfigByCreateIndex.getFieldName();
                String parentFieldName = initConfigByCreateIndex.getParentFieldName();
                boolean parentStatus = initConfigByCreateIndex.getParentStatus();
                initCreateIndex(client, initConfigByCreateIndex, objectList.get(0));
                for (Object object : objectList) {
                    Class cls = object.getClass();
                    Field[] fields = cls.getDeclaredFields();
                    Object fieldNameObject = null;
                    Object parentFieldNameObject = null;
                    int i = 0;
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (parentStatus && i == 2) {
                            break;
                        } else if (!parentStatus && i == 1) {
                            break;
                        } else if (fieldName.equals(field.getName())) {
                            fieldNameObject = field.get(object);
                            i += 1;
                        } else if (parentFieldName.equals(field.getName())) {
                            parentFieldNameObject = field.get(object);
                            i += 1;
                        }
                    }
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (fieldName.equals(field.getName())) {
                            IndexRequestBuilder indexRequestBuilder = client.prepareIndex(indexName,
                                        cls.getSimpleName());
                            // 必须为对象单独指定ID
                            indexRequestBuilder.setId(fieldNameObject.toString());
                            indexRequestBuilder.setSource(ESUtils.toJson(object));
                            if (parentStatus) {
                                indexRequestBuilder.setParent(parentFieldNameObject.toString());
                            }
                            indexRequestBuilder.execute().actionGet();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param searchRequestBuilder
     * @return 根据检索条件获取结果
     */
    public static SearchHits getSearchResponseHits(SearchRequestBuilder searchRequestBuilder) {
        SearchResponse actionGet = searchRequestBuilder.execute().actionGet();
        return actionGet.getHits();
    }

    /**
     * @param searchHits
     * @return 根据检索结果组装成list
     */
    public static List<Map<String, Object>> getSearchResponseList(SearchHits searchHits) {
        List<Map<String, Object>> matchResult = new LinkedList<Map<String, Object>>();
        for (SearchHit hit : searchHits.getHits()) {
            matchResult.add(hit.getSource());
        }
        return matchResult;
    }

    public static GetResponse getById(Client client, String serverName, String className, String id) {
        GetResponse response = client.prepareGet(serverName, className, id).execute().actionGet();
        return response;
    }

    public static String toJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
