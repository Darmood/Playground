package it.devlec.json;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class EsempioJSON {
    private static final Logger logger = LogManager.getLogger(EsempioJSON.class);

    public void esempioJSONOggetto(){
        JSONObject jo = new JSONObject();
        jo.put("nome", "Giovanni");
        jo.put("eta", "22");
        jo.put("citta", "Lecce");
        logger.info("JSON "+ jo);
    }
    public void esempioJSONArray(){
        JSONArray ja = new JSONArray();
        ja.put(Boolean.TRUE);
        ja.put("Ciao a tutti");

        JSONObject jo = new JSONObject();
        jo.put("Nome", "Luca");
        jo.put("eta", "67");
        jo.put("citta", "Roma");

        ja.put(jo);
        logger.info("JSON "+ ja);
    }
}
