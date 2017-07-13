package com.shandian.CommonUtility.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class UseLog4J2 {
    private static final Logger LOGGER = LogManager.getLogger(UseLog4J2.class);

    @Test
    public void testLo4j2() {
        LOGGER.info(System.getProperties().get("user.home"));
        LOGGER.error("hello world!");
    }
}
