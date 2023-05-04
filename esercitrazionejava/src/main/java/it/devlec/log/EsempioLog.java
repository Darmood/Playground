package it.devlec.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EsempioLog {
    private static final Logger logger =  LogManager.getLogger(EsempioLog.class);

    public EsempioLog(){

    }
    public void stampaAltriLog(){
        logger.info("Ciao da stampa altri log...");
    }
}
