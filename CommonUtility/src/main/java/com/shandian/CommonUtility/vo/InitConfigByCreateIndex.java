package com.shandian.CommonUtility.vo;

/**
 * Created by linqs on 2017/1/8.
 */
public class InitConfigByCreateIndex {
    /**
     * 索引名 类似mysql的库名 例子:money_server_debug
     */
    private String indexName;
    /**
     * 指定id 类似mysql的自增id 以这个来更新
     */
    private String fieldName;
    /**
     * true 本身变成子类
     */
    private boolean parentStatus = false;
    /**
     * 父类的typeName 类似mysql的表名 例子OrderStateSearch
     */
    private String parentName;
    /**
     * 指定父类关联id 类似mysqld left jion t1.id=t2.parent_id 用来关联的t2.parent_id
     */
    private String parentFieldName = "";

    public InitConfigByCreateIndex() {}

    public InitConfigByCreateIndex(String indexName, String fieldName, boolean parentStatus, String parentName,
                String parentFieldName) {
        this.indexName = indexName;
        this.fieldName = fieldName;
        this.parentStatus = parentStatus;
        this.parentName = parentName;
        this.parentFieldName = parentFieldName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public boolean getParentStatus() {
        return parentStatus;
    }

    public void setParentStatus(boolean parentStatus) {
        this.parentStatus = parentStatus;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public boolean isParentStatus() {
        return parentStatus;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getParentFieldName() {
        return parentFieldName;
    }

    public void setParentFieldName(String parentFieldName) {
        this.parentFieldName = parentFieldName;
    }

    @Override
    public String toString() {
        return "InitConfigByCreateIndex{" + "indexName='" + indexName + '\'' + ", fieldName='" + fieldName + '\''
                    + ", parentStatus=" + parentStatus + ", parentName='" + parentName + '\'' + ", parentFieldName='"
                    + parentFieldName + '\'' + '}';
    }
}
