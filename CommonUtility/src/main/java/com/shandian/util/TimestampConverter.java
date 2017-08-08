package com.shandian.util;

import org.apache.commons.beanutils.converters.DateTimeConverter;

public class TimestampConverter extends DateTimeConverter {
    @Override
    public Object convert(Class type, Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof java.sql.Timestamp) {
            return value;
        }
        return value;
    }

    @Override
    protected Class getDefaultType() {
        return java.sql.Timestamp.class;
    }
}
