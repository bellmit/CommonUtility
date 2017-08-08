package com.shandian.CommonUtility.vo.excel;

import java.io.Serializable;

public class Heads implements Serializable {
    private static final long serialVersionUID = 1L;
    private int firstRow;
    private int lastRow;
    private int firstCol;
    private int lastCol;
    private String fieldName;

    public Heads(int firstRow, int lastRow, int firstCol, int lastCol, String fieldName) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
        this.fieldName = fieldName;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public int getFirstCol() {
        return firstCol;
    }

    public void setFirstCol(int firstCol) {
        this.firstCol = firstCol;
    }

    public int getLastCol() {
        return lastCol;
    }

    public void setLastCol(int lastCol) {
        this.lastCol = lastCol;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
