package it.devlec.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EsempioLog {
    private static final Logger logger =  LogManager.getLogger(EsempioLog.class);

    public EsempioLog(){

    }
    public void stampaAltriLog(){
        logger.trace("Log4j 2 - test trace");
        logger.debug("Log4j 2 - test debug");
        logger.info("Log4j 2 - test info");
        logger.warn("Log4j 2 - test warn");
        logger.error("Log4j 2 - test error");
        logger.fatal("Log4j 2 - test fatal");
    }
}
