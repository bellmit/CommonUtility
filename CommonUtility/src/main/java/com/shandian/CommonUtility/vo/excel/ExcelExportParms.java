package com.shandian.CommonUtility.vo.excel;

import java.io.Serializable;
import java.util.List;

public class ExcelExportParms implements Serializable {
    private static final long serialVersionUID = 1L;
    List<?> list;
    String[] fields;
    String fileName;
    List<Heads> headList;
    int headRow;

    public ExcelExportParms(List<?> list, String[] fields, String fileName, List<Heads> headList, int headRow) {
        this.list = list;
        this.fields = fields;
        this.fileName = fileName;
        this.headList = headList;
        this.headRow = headRow;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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

    public List<Heads> getHeadList() {
        return headList;
    }

    public void setHeadList(List<Heads> headList) {
        this.headList = headList;
    }

    public int getHeadRow() {
        return headRow;
    }

    public void setHeadRow(int headRow) {
        this.headRow = headRow;
    }
}
