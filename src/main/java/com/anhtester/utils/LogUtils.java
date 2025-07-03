package com.anhtester.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {
    //Initialize Log4j instance
    private static final Logger logger = LogManager.getLogger(LogUtils.class);

    //Info Level Logs
    public static void info(String message) {
        logger.info(message);
    }

    public static void info(Object object) {
        logger.info(object);
    }

    //Warn Level Logs
    public static void warn(String message) {
        logger.warn(message);
    }

    public static void warn(Object object) {
        logger.warn(object);
    }

    //Error Level Logs
    public static void error(String message) {
        logger.error(message);
    }

    public static void error(Object object) {
        logger.error(object);
    }

    //Fatal Level Logs
    public static void fatal(String message) {
        logger.fatal(message);
    }

    //Debug Level Logs
    public static void debug(String message) {
        logger.debug(message);
    }

    public static void debug(Object object) {
        logger.debug(object);
    }
}