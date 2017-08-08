package com.shandian.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shandian.CommonUtility.vo.excel.ExcelExportParms;
import com.shandian.CommonUtility.vo.excel.ExcelExportSingleHeadParms;
import com.shandian.CommonUtility.vo.excel.Heads;

public class ExcelExportUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExcelExportUtil.class);

    /**
     * 多表头多个sheet,double类型转换百分比
     * 
     * @param response
     * @param listpaParms 每个sheet的参数
     * @param fileName excel名称
     * @author wangwk 2015-4-22
     */
    @SuppressWarnings("unchecked")
    public static void exportExcelMultipleSheets(OutputStream os, List<ExcelExportParms> listpaParms, String fileName) {
        try {
            // 设置response参数
            XSSFWorkbook wb = new XSSFWorkbook();
            //
            for (ExcelExportParms excelExportParms : listpaParms) {
                List<?> list = excelExportParms.getList();
                String[] fields = excelExportParms.getFields();
                List<Heads> headList = excelExportParms.getHeadList();
                String sheetName = excelExportParms.getFileName();
                int headRow = excelExportParms.getHeadRow();
                //
                Sheet sheet = wb.createSheet(sheetName);
                // 设置单元格的文字格式
                sheet.setDefaultColumnWidth((short) 15);
                // 设置单元格的文字格式
                XSSFCellStyle style = wb.createCellStyle();
                style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                // style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
                // style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
                // style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
                // style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
                //
                for (int i = 0; i < headRow; i++) {
                    sheet.createRow(i);
                }
                // 设置cell
                for (int i = 0; i < headList.size(); i++) {
                    Heads myHeads = headList.get(i);
                    Row rowHead = sheet.getRow(myHeads.getFirstRow());
                    Cell cellHead = rowHead.createCell(myHeads.getFirstCol());
                    cellHead.setCellStyle(style);
                    cellHead.setCellValue(myHeads.getFieldName());
                    sheet.addMergedRegion(new CellRangeAddress(myHeads.getFirstRow(), myHeads.getLastRow(), myHeads
                                .getFirstCol(), myHeads.getLastCol()));
                }
                // 设置列头名
                Iterator iterator = list.iterator();
                //
                int index = sheet.getPhysicalNumberOfRows();
                while (iterator.hasNext()) {
                    Object object = iterator.next();
                    Row row = sheet.createRow(index);
                    // 通过反射获得方法
                    for (int i = 0; i < fields.length; i++) {
                        Class tCls = object.getClass();
                        String getMethodName = "get" + fields[i].substring(0, 1).toUpperCase() + fields[i].substring(1);
                        Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                        Object value = getMethod.invoke(object, new Object[]{});
                        if (value instanceof Double) {
                            NumberFormat fmt = NumberFormat.getPercentInstance();
                            fmt.setMaximumFractionDigits(2);// 最多两位百分小数，如25.23%
                            String result = fmt.format(value);
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue(result);
                        } else if (value instanceof Integer) {
                            Integer cellVal = Integer.parseInt(value.toString());
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue(cellVal);
                        } else if (value instanceof BigDecimal) {
                            Double cellVal = Double.parseDouble(value.toString());
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue(cellVal);
                        } else {
                            if (value == null) {
                                Cell cell = row.createCell(i);
                                cell.setCellStyle(style);
                                cell.setCellValue("");
                            } else {
                                String textValue = value.toString();
                                Cell cell = row.createCell(i);
                                cell.setCellStyle(style);
                                cell.setCellValue(textValue);
                            }
                        }
                    }
                    index++;
                }
            }
            // 设置内容
            wb.write(os);
            logger.info("导出excel成功!");
        } catch (Exception e) {
            logger.info("exportExcelMultipleSheets export wrong!");
            e.printStackTrace();
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                logger.info("exportExcelMultipleSheets closed wrong!");
                e.printStackTrace();
            }
        }
    }

    /**
     * 导出excel多表头多sheet 数据单列 对double类型转换为百分比
     *
     * @param response
     * @param list 数据list
     * @param fields 要导出的数据字段
     * @param fileName 文件名称
     * @param headList 多表头设置
     * @param headRow 表头行数
     * @author wangwk 2015-4-22
     */
    @SuppressWarnings("unchecked")
    public static void exportExcelMultiple(OutputStream os, List<?> list, String[] fields, String fileName,
                List<Heads> headList, int headRow) {
        try {
            // 设置response参数
            XSSFWorkbook wb = new XSSFWorkbook();
            //
            Sheet sheet = wb.createSheet(fileName);
            sheet.setDefaultColumnWidth((short) 15);
            // 设置单元格的文字格式
            XSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            // style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
            // style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
            // style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
            // style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
            //
            // 设置列头名
            for (int i = 0; i < headRow; i++) {
                sheet.createRow(i);
            }
            // 设置cell
            for (int i = 0; i < headList.size(); i++) {
                Heads myHeads = headList.get(i);
                Row rowHead = sheet.getRow(myHeads.getFirstRow());
                Cell cellHead = rowHead.createCell(myHeads.getFirstCol());
                cellHead.setCellStyle(style);
                cellHead.setCellValue(myHeads.getFieldName());
                sheet.addMergedRegion(new CellRangeAddress(myHeads.getFirstRow(), myHeads.getLastRow(), myHeads
                            .getFirstCol(), myHeads.getLastCol()));
            }
            // 设置内容
            Iterator iterator = list.iterator();
            //
            int index = sheet.getPhysicalNumberOfRows();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                Row row = sheet.createRow(index);
                // 通过反射获得方法
                for (int i = 0; i < fields.length; i++) {
                    Class tCls = object.getClass();
                    String getMethodName = "get" + fields[i].substring(0, 1).toUpperCase() + fields[i].substring(1);
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(object, new Object[]{});
                    //
                    if (value instanceof Double) {
                        NumberFormat fmt = NumberFormat.getPercentInstance();
                        fmt.setMaximumFractionDigits(2);// 最多两位百分小数，如25.23%
                        String result = fmt.format(value);
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(result);
                    } else if (value instanceof Integer) {
                        Integer cellVal = Integer.parseInt(value.toString());
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else if (value instanceof BigDecimal) {
                        Double cellVal = Double.parseDouble(value.toString());
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else {
                        if (value == null) {
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue("");
                        } else {
                            String textValue = value.toString();
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue(textValue);
                        }
                    }
                }
                index++;
            }
            // 内容格式
            // 设置内容
            wb.write(os);
            logger.info("导出excel成功!");
        } catch (Exception e) {
            logger.error("exportExcelMultiple export wrong!", e);
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                logger.error("exportExcelMultiple closed wrong!", e);
            }
        }
    }

    /**
     * 导出excel多表头多sheet 数据单列 ,double类型截取2位小数点
     *
     * @param response
     * @param list 数据list
     * @param fields 要导出的数据字段
     * @param fileName 文件名称
     * @param headList 多表头设置
     * @param headRow 表头行数
     * @author wangwk 2015-4-22
     */
    @SuppressWarnings("unchecked")
    public static void exportExcelMultiple2(OutputStream os, List<?> list, String[] fields, String fileName,
                List<Heads> headList, int headRow) {
        try {
            // 设置response参数
            XSSFWorkbook wb = new XSSFWorkbook();
            //
            Sheet sheet = wb.createSheet(fileName);
            sheet.setDefaultColumnWidth((short) 15);
            // 设置单元格的文字格式
            XSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            // style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
            // style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
            // style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
            // style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
            //
            // 设置列头名
            for (int i = 0; i < headRow; i++) {
                sheet.createRow(i);
            }
            // 设置cell
            for (int i = 0; i < headList.size(); i++) {
                Heads myHeads = headList.get(i);
                Row rowHead = sheet.getRow(myHeads.getFirstRow());
                Cell cellHead = rowHead.createCell(myHeads.getFirstCol());
                cellHead.setCellStyle(style);
                cellHead.setCellValue(myHeads.getFieldName());
                sheet.addMergedRegion(new CellRangeAddress(myHeads.getFirstRow(), myHeads.getLastRow(), myHeads
                            .getFirstCol(), myHeads.getLastCol()));
            }
            // 设置内容
            Iterator iterator = list.iterator();
            //
            int index = sheet.getPhysicalNumberOfRows();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                Row row = sheet.createRow(index);
                // 通过反射获得方法
                for (int i = 0; i < fields.length; i++) {
                    Class tCls = object.getClass();
                    String getMethodName = "get" + fields[i].substring(0, 1).toUpperCase() + fields[i].substring(1);
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(object, new Object[]{});
                    //
                    if (value instanceof Double) {
                        // NumberFormat fmt = NumberFormat.getInstance();
                        // fmt.setMaximumFractionDigits(2);// 保留2位小数点
                        // String result = fmt.format(value);
                        DecimalFormat df = new DecimalFormat("#.00");
                        String result = df.format(value);
                        Double cellVal = Double.parseDouble(result);
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else if (value instanceof Integer) {
                        Integer cellVal = Integer.parseInt(value.toString());
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else if (value instanceof BigDecimal) {
                        Double cellVal = Double.parseDouble(value.toString());
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else {
                        if (value == null) {
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue("");
                        } else {
                            String textValue = value.toString();
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue(textValue);
                        }
                    }
                }
                index++;
            }
            // 内容格式
            // 设置内容
            wb.write(os);
            logger.info("导出excel成功!");
        } catch (Exception e) {
            logger.error("exportExcelMultiple export wrong!", e);
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                logger.error("exportExcelMultiple closed wrong!", e);
            }
        }
    }

    /**
     * 单表头普通导出excel,double类型转换百分比
     *
     * @param response
     * @param list 数据
     * @param titles 标题
     * @param fields 导出内容字段
     * @param fileName 文件名称
     * @author wangwk 2015-4-22
     */
    @SuppressWarnings("unchecked")
    public static void exportExcelSingle(OutputStream os, List<?> list, String[] titles, String[] fields,
                String fileName) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet(fileName);
            sheet.setDefaultColumnWidth((short) 15);
            // 设置单元格的文字格式
            XSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            // style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
            // style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
            // style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
            // style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
            // 设置列头名
            Row rowHead = sheet.createRow(0);
            for (int j = 0; j < titles.length; j++) {
                Cell cell = rowHead.createCell(j);
                cell.setCellStyle(style);
                cell.setCellValue(titles[j]);
            }
            // 设置内容
            Iterator iterator = list.iterator();
            //
            int index = 0;// sheet.getPhysicalNumberOfRows();
            while (iterator.hasNext()) {
                index++;
                Object object = iterator.next();
                Row row = sheet.createRow(index);
                // 通过反射获得方法
                for (int i = 0; i < fields.length; i++) {
                    Class tCls = object.getClass();
                    String getMethodName = "get" + fields[i].substring(0, 1).toUpperCase() + fields[i].substring(1);
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(object, new Object[]{});
                    //
                    //
                    if (value instanceof Double) {
                        NumberFormat fmt = NumberFormat.getPercentInstance();
                        fmt.setMaximumFractionDigits(2);// 最多两位百分小数，如25.23%
                        String result = fmt.format(value);
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(result);
                    } else if (value instanceof Integer) {
                        Integer cellVal = Integer.parseInt(value.toString());
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else if (value instanceof BigDecimal) {
                        Double cellVal = Double.parseDouble(value.toString());
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else {
                        if (value == null) {
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue("");
                        } else {
                            String textValue = value.toString();
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue(textValue);
                        }
                    }
                }
            }
            // 写入
            wb.write(os);
            logger.info("导出excel成功!");
        } catch (Exception e) {
            logger.error("exportExcelSingle export wrong!", e);
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                logger.error("exportExcelSingle closed wrong!", e);
            }
        }
    }

    /**
     * 单表头普通导出excel,double类型截取2位小数点
     *
     * @param response
     * @param list 数据
     * @param titles 标题
     * @param fields 导出内容字段
     * @param fileName 文件名称
     * @author wangwk 2015-4-22
     */
    @SuppressWarnings("unchecked")
    public static void exportExcelSingle2(OutputStream os, List<?> list, String[] titles, String[] fields,
                String fileName) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet(fileName);
            sheet.setDefaultColumnWidth((short) 15);
            // 设置单元格的文字格式
            XSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            // style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
            // style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
            // style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
            // style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
            // 设置列头名
            Row rowHead = sheet.createRow(0);
            for (int j = 0; j < titles.length; j++) {
                Cell cell = rowHead.createCell(j);
                cell.setCellStyle(style);
                cell.setCellValue(titles[j]);
            }
            // 设置内容
            Iterator iterator = list.iterator();
            //
            int index = 0;// sheet.getPhysicalNumberOfRows();
            while (iterator.hasNext()) {
                index++;
                Object object = iterator.next();
                Row row = sheet.createRow(index);
                // 通过反射获得方法
                for (int i = 0; i < fields.length; i++) {
                    Class tCls = object.getClass();
                    String getMethodName = "get" + fields[i].substring(0, 1).toUpperCase() + fields[i].substring(1);
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(object, new Object[]{});
                    //
                    //
                    if (value instanceof Double) {
                        DecimalFormat df = new DecimalFormat("#.00");
                        String result = df.format(value);
                        Double cellVal = Double.parseDouble(result);
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else if (value instanceof Integer) {
                        Integer cellVal = Integer.parseInt(value.toString());
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else if (value instanceof BigDecimal) {
                        Double cellVal = Double.parseDouble(value.toString());
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else {
                        if (value == null) {
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue("");
                        } else {
                            String textValue = value.toString();
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue(textValue);
                        }
                    }
                }
            }
            // 写入
            wb.write(os);
            logger.info("导出excel成功!");
        } catch (Exception e) {
            logger.error("exportExcelSingle2 export wrong!", e);
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                logger.error("exportExcelSingle2 closed wrong!", e);
            }
        }
    }

    /**
     * <h2>单表头普通导出excel,double类型截取2位小数点</h2> <h2>数据区域单元格格式均为文本，目前设计opr&depot的导出使用</h2>
     *
     * @param response
     * @param list 数据
     * @param titles 标题
     * @param fields 导出内容字段
     * @param fileName 文件名称
     * @author guomingyi 2017-06-13
     */
    @SuppressWarnings("unchecked")
    public static void exportExcelSingle2WithTextCell(OutputStream os, List<?> list, String[] titles, String[] fields,
                String fileName) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet(fileName);
            sheet.setDefaultColumnWidth((short) 15);
            // 设置单元格的文字格式
            XSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            // 设置列头名
            Row rowHead = sheet.createRow(0);
            for (int j = 0; j < titles.length; j++) {
                Cell cell = rowHead.createCell(j);
                cell.setCellStyle(style);
                cell.setCellValue(titles[j]);
            }
            if (list == null) {
                wb.write(os);
                return;
            }
            // 设置内容
            Iterator iterator = list.iterator();
            //
            int index = 0;// sheet.getPhysicalNumberOfRows();
            DecimalFormat df = new DecimalFormat("0.00");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            CellStyle textStyle = ExcelExportUtil.getTextStyle(wb);
            while (iterator.hasNext()) {
                index++;
                Object object = iterator.next();
                Row row = sheet.createRow(index);
                // 通过反射获得方法
                for (int i = 0; i < fields.length; i++) {
                    Class tCls = object.getClass();
                    String getMethodName = "get" + fields[i].substring(0, 1).toUpperCase() + fields[i].substring(1);
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(object, new Object[]{});
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(textStyle);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    //
                    //
                    if (value instanceof Double) {
                        String result = df.format(value);
                        Double cellVal = Double.parseDouble(result);
                        cell.setCellValue(cellVal);
                    } else if (value instanceof Integer) {
                        Integer cellVal = Integer.parseInt(value.toString());
                        cell.setCellValue(cellVal);
                    } else if (value instanceof BigDecimal) {
                        Double cellVal = Double.parseDouble(value.toString());
                        cell.setCellValue(cellVal);
                    } else if (value instanceof Date) {
                        String cellVal = sdf.format((Date) value);
                        cell.setCellValue(cellVal);
                    } else {
                        if (value == null) {
                            cell.setCellValue("");
                        } else {
                            String textValue = value.toString();
                            cell.setCellValue(textValue);
                        }
                    }
                }
            }
            // 写入
            wb.write(os);
            logger.info("导出excel成功!");
        } catch (Exception e) {
            logger.error("exportExcelSingle2 export wrong!", e);
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                logger.error("exportExcelSingle2 closed wrong!", e);
            }
        }
    }

    /**
     * 单表头普通导出excel,不做处理
     *
     * @param response
     * @param list 数据
     * @param titles 标题
     * @param fields 导出内容字段
     * @param fileName 文件名称
     * @author wangwk 2015-4-22
     */
    @SuppressWarnings("unchecked")
    public static void exportExcelSingle3(OutputStream os, List<?> list, String[] titles, String[] fields,
                String fileName) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            //
            Sheet sheet = wb.createSheet(fileName);
            sheet.setDefaultColumnWidth((short) 15);
            // 设置单元格的文字格式
            XSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            // style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
            // style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
            // style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
            // style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
            // 设置列头名
            Row rowHead = sheet.createRow(0);
            for (int j = 0; j < titles.length; j++) {
                Cell cell = rowHead.createCell(j);
                cell.setCellStyle(style);
                cell.setCellValue(titles[j]);
            }
            // 设置内容
            Iterator iterator = list.iterator();
            //
            int index = 0;// sheet.getPhysicalNumberOfRows();
            while (iterator.hasNext()) {
                index++;
                Object object = iterator.next();
                Row row = sheet.createRow(index);
                // 通过反射获得方法
                for (int i = 0; i < fields.length; i++) {
                    Class tCls = object.getClass();
                    String getMethodName = "get" + fields[i].substring(0, 1).toUpperCase() + fields[i].substring(1);
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(object, new Object[]{});
                    //
                    //
                    if (value instanceof Double) {
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue((Double) value);
                    } else if (value instanceof Integer) {
                        Integer cellVal = Integer.parseInt(value.toString());
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else if (value instanceof BigDecimal) {
                        Double cellVal = Double.parseDouble(value.toString());
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else {
                        if (value == null) {
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue("");
                        } else {
                            String textValue = value.toString();
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue(textValue);
                        }
                    }
                }
            }
            // 写入
            wb.write(os);
            logger.info("导出excel成功!");
        } catch (Exception e) {
            logger.error("exportExcelSingle3 export wrong!", e);
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                logger.error("exportExcelSingle3 closed wrong!", e);
            }
        }
    }

    /**
     * 导出单表头多个sheet ,保留2位小数
     *
     * @param response
     * @param listParms
     * @param fileName
     * @author wangwk 2015-9-25
     */
    @SuppressWarnings("unchecked")
    public static void exportExcelSingleSheets(OutputStream os, List<ExcelExportSingleHeadParms> listParms,
                String fileName) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            for (ExcelExportSingleHeadParms excelExportParms : listParms) {
                List<?> list = excelExportParms.getList();
                String[] fields = excelExportParms.getFields();
                String[] headLis = excelExportParms.getHeads();
                String sheetName = excelExportParms.getFileName();
                //
                //
                Sheet sheet = wb.createSheet(sheetName);
                sheet.setDefaultColumnWidth((short) 15);
                // 设置单元格的文字格式
                XSSFCellStyle style = wb.createCellStyle();
                style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                // style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
                // style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
                // style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
                // style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
                // 设置列头名
                Row rowHead = sheet.createRow(0);
                for (int j = 0; j < headLis.length; j++) {
                    Cell cell = rowHead.createCell(j);
                    cell.setCellStyle(style);
                    cell.setCellValue(headLis[j]);
                }
                // 设置内容
                Iterator iterator = list.iterator();
                //
                int index = 0;// sheet.getPhysicalNumberOfRows();
                while (iterator.hasNext()) {
                    index++;
                    Object object = iterator.next();
                    Row row = sheet.createRow(index);
                    for (int i = 0; i < fields.length; i++) {
                        Object value = null;
                        if (object instanceof Map) {
                            Map<String, Object> mapOb = (Map<String, Object>) object;
                            value = mapOb.get(fields[i]);
                        } else {
                            // 通过反射获得方法
                            Class tCls = object.getClass();
                            String getMethodName = "get" + fields[i].substring(0, 1).toUpperCase()
                                        + fields[i].substring(1);
                            Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                            value = getMethod.invoke(object, new Object[]{});
                        }
                        //
                        //
                        if (value instanceof Double) {
                            DecimalFormat df = new DecimalFormat("#.00");
                            String result = df.format(value);
                            Double cellVal = Double.parseDouble(result);
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue(cellVal);
                        } else if (value instanceof Integer) {
                            Integer cellVal = Integer.parseInt(value.toString());
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue(cellVal);
                        } else if (value instanceof BigDecimal) {
                            Double cellVal = Double.parseDouble(value.toString());
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue(cellVal);
                        } else {
                            if (value == null) {
                                Cell cell = row.createCell(i);
                                cell.setCellStyle(style);
                                cell.setCellValue("");
                            } else {
                                String textValue = value.toString();
                                Cell cell = row.createCell(i);
                                cell.setCellStyle(style);
                                cell.setCellValue(textValue);
                            }
                        }
                    }
                }
            }
            // 写入
            wb.write(os);
            logger.info("导出excel成功!");
        } catch (Exception e) {
            logger.error("exportExcelSingleSheets export wrong!", e);
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                logger.error("exportExcelSingleSheets closed wrong!", e);
            }
        }
    }

    /**
     * 导出excel多表头多sheet 数据单列 ,double类型截取2位小数点
     *
     * @param response
     * @param list<Map<String, Object>> 数据list
     * @param fields 要导出的数据字段
     * @param fileName 文件名称
     * @param headList 多表头设置
     * @param headRow 表头行数
     * @author wangwk 2015-4-22
     */
    public static void exportExcelMultipleMap2(OutputStream os, List<Map<String, Object>> list, String[] fields,
                String fileName, List<Heads> headList, int headRow) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet(fileName);
            sheet.setDefaultColumnWidth((short) 15);
            // 设置单元格的文字格式
            XSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            // style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
            // style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
            // style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
            // style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
            //
            // 设置列头名
            for (int i = 0; i < headRow; i++) {
                sheet.createRow(i);
            }
            // 设置cell
            for (int i = 0; i < headList.size(); i++) {
                Heads myHeads = headList.get(i);
                Row rowHead = sheet.getRow(myHeads.getFirstRow());
                Cell cellHead = rowHead.createCell(myHeads.getFirstCol());
                cellHead.setCellStyle(style);
                cellHead.setCellValue(myHeads.getFieldName());
                sheet.addMergedRegion(new CellRangeAddress(myHeads.getFirstRow(), myHeads.getLastRow(), myHeads
                            .getFirstCol(), myHeads.getLastCol()));
            }
            //
            int index = 0;// sheet.getPhysicalNumberOfRows();
            for (Map<String, Object> objectMap : list) {
                index++;
                Row row = sheet.createRow(index);
                for (int i = 0; i < fields.length; i++) {
                    Object value = objectMap.get(fields[i]);
                    //
                    if (value instanceof Double) {
                        DecimalFormat df = new DecimalFormat("#.00");
                        String result = df.format(value);
                        Double cellVal = Double.parseDouble(result);
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else if (value instanceof Integer) {
                        Integer cellVal = Integer.parseInt(value.toString());
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else if (value instanceof BigDecimal) {
                        Double cellVal = Double.parseDouble(value.toString());
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else {
                        if (value == null) {
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue("");
                        } else {
                            String textValue = value.toString();
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue(textValue);
                        }
                    }
                }
                index++;
            }
            // 内容格式
            // 设置内容
            wb.write(os);
            logger.info("导出excel成功!");
        } catch (Exception e) {
            logger.error("exportExcelMultipleMap2 export wrong!", e);
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                logger.error("exportExcelMultipleMap2 closed wrong!", e);
            }
        }
    }

    /**
     * 单表头普通导出excel,数据List<Map<String, Object>>,double截取2位小数
     *
     * @param response
     * @param list 数据map<String,object>
     * @param titles 标题
     * @param fields 导出内容字段
     * @param fileName 文件名称
     * @author wangwk 2015-11-16
     */
    public static void exportExcelSingleMap(OutputStream os, List<Map<String, Object>> list, String[] titles,
                String[] fields, String fileName) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet(fileName);
            for (int i = 0; i < fields.length; i++) {
                sheet.setColumnWidth(i, 12 * 256);
            }
            // 设置单元格的文字格式
            XSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            // style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
            // style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
            // style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
            // style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
            // 设置列头名
            Row rowHead = sheet.createRow(0);
            for (int j = 0; j < titles.length; j++) {
                Cell cell = rowHead.createCell(j);
                cell.setCellStyle(style);
                cell.setCellValue(titles[j]);
            }
            // 设置内容
            //
            int index = 0;// sheet.getPhysicalNumberOfRows();
            for (Map<String, Object> objectMap : list) {
                index++;
                Row row = sheet.createRow(index);
                for (int i = 0; i < fields.length; i++) {
                    Object value = objectMap.get(fields[i]);
                    //
                    if (value instanceof Double) {
                        DecimalFormat df = new DecimalFormat("#.00");
                        String result = df.format(value);
                        Double cellVal = Double.parseDouble(result);
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else if (value instanceof Integer) {
                        Integer cellVal = Integer.parseInt(value.toString());
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else if (value instanceof BigDecimal) {
                        Double cellVal = Double.parseDouble(value.toString());
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        cell.setCellValue(cellVal);
                    } else {
                        if (value == null) {
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue("");
                        } else {
                            String textValue = value.toString();
                            Cell cell = row.createCell(i);
                            cell.setCellStyle(style);
                            cell.setCellValue(textValue);
                        }
                    }
                }
            }
            // 写入
            wb.write(os);
            logger.info("导出excel成功!");
        } catch (Exception e) {
            logger.error("exportExcelSingleMap export wrong!", e);
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                logger.error("exportExcelSingleMap closed wrong!", e);
            }
        }
    }

    /**
     * 横向简单导出excel，无特殊格式要求
     *
     * @param list 需要导出的数据
     * @param titles excel标题数组
     * @param fields List数组所属类的属性数组,例：List<People>的People有height与 weight两个属性，则fields为String数组{"height","weight"}
     * @param response
     * @param cacheRow 缓存行数
     */
    public static void exportSXSSFWorkbook(List list, Object[] titles, Object[] fields, String fileName,
                OutputStream os, int cacheRow) {
        Workbook wwb = null;
        try {
            wwb = new SXSSFWorkbook(cacheRow); // 建立excel文件,缓存行数
            Sheet ws = wwb.createSheet(); // 创建一个工作表
            // // 设置单元格的文字格式
            // WritableFont wf1 = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false,
            // UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            // WritableCellFormat wcf = new WritableCellFormat(wf1);
            // wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
            // wcf.setAlignment(Alignment.CENTRE);
            CellStyle titleStyle = wwb.createCellStyle();
            Font titleFont = wwb.createFont();
            titleFont.setFontHeight((short) 12);
            titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
            DataFormat format = wwb.createDataFormat();
            titleStyle.setDataFormat(format.getFormat("@"));
            // 设置列头名
            Row row = ws.createRow(0);
            for (int j = 0; j < titles.length; j++) {
                String title = String.valueOf(titles[j]);
                Cell cell = row.createCell(j);
                cell.setCellValue(title);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellStyle(titleStyle);
            }
            // // 内容格式
            // WritableFont wf2 = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false,
            // UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            // wcf = new WritableCellFormat(wf2, NumberFormats.TEXT);
            // wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
            // wcf.setAlignment(Alignment.CENTRE);
            // 设置内容
            if (list != null && list.size() > 0) {
                int i = 0;
                for (Object object : list) {
                    int j = 0;
                    Map<String, Object> map = (object instanceof Map) ? (Map<String, Object>) object : BeanUtils
                                .beanToMap(object);
                    row = ws.createRow(i + 1);
                    for (Object name : fields) {
                        String param = "";
                        if (map.get(name) != null) {
                            param = map.get(name).toString();
                        }
                        if (param == null) {
                            param = "";
                        }
                        // byte[] pb = param.getBytes();
                        Cell cell = row.createCell(j);
                        cell.setCellValue(param);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cell.setCellStyle(titleStyle);
                        j++;
                    }
                    i++;
                }
            }
            wwb.write(os);
        } catch (Exception e) {} finally {
            try {
                wwb.close();
                os.close();
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }
    }

    private static CellStyle getTextStyle(XSSFWorkbook wb) {
        XSSFCellStyle textStyle = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        textStyle.setDataFormat(format.getFormat("@"));
        return textStyle;
    }

    public static List<Heads> getHeadListByObjectHeadList(List<Object[]> objectHeadList) {
        List<Heads> headsList = new ArrayList<>(objectHeadList.size());
        for (Object[] objects : objectHeadList) {
            headsList.add(new Heads((int) objects[0], (int) objects[1], (int) objects[2], (int) objects[3],
                        (String) objects[4]));
        }
        return headsList;
    }
}
