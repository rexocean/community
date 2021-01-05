package com.nowcoder.community;


import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
//测试时以主程序作为配置类
@ContextConfiguration(classes = CommunityApplication.class)
public class LoggerTests {

    //slf4j
    private static final Logger logger = LoggerFactory.getLogger(LoggerTests.class);


    @Test
    public void testLogger() {
        System.out.println(logger.getName());

        logger.trace("trace logger");
        logger.debug("debug logger");
        logger.info("info logger");
        logger.warn("warn logger");
        logger.error("error logger");

    }
}
