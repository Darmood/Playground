package it.devlec.json;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class EsempioJSON {
    private static final Logger logger = LogManager.getLogger(EsempioJSON.class);

    public JSONObject JSONOggetto(){
        JSONObject jo = new JSONObject();
        jo.put("nome", "Mario");
        jo.put("cognome", "Rossi");
        jo.put("eta", "72");
        jo.put("citta", "Lecce");

        return jo;
    }
    public JSONArray JSONArray(){
        JSONArray ja = new JSONArray();
        ja.put(Boolean.TRUE);
        ja.put("Secondo elem");
        ja.put(JSONOggetto());
        ja.put(JSONOggetto());
        ja.put("Utimo elem");

        return ja;
    }
}
