package com.shandian.CommonUtility.vo.excel;

import java.io.Serializable;
import java.util.List;

public class ExcelExportSingleHeadParms implements Serializable {
    private static final long serialVersionUID = 1L;
    List<?> list;
    String[] fields;
    String fileName;
    String[] heads;

    public ExcelExportSingleHeadParms() {}

    public ExcelExportSingleHeadParms(List<?> list, String[] fields, String fileName, String[] heads) {
        this.list = list;
        this.fields = fields;
        this.fileName = fileName;
        this.heads = heads;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getHeads() {
        return heads;
    }

    public void setHeads(String[] heads) {
        this.heads = heads;
    }
}
