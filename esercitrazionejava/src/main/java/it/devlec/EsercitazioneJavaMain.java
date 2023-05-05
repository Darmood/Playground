package it.devlec;

import it.devlec.csv.EsempioCSV;
import it.devlec.excel.EsempioExcel;
import it.devlec.json.EsempioJSON;
import it.devlec.log.EsempioLog;
import it.devlec.pdf.EsempioPDF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class EsercitazioneJavaMain {
    private static final Logger logger =  LogManager.getLogger(EsercitazioneJavaMain.class);
    public static void main(String[] args){

//        EsempioLog esempioLog = new EsempioLog();
//        esempioLog.stampaAltriLog();

//        EsempioCSV esempioCSV = new EsempioCSV();
//        esempioCSV.leggiCSV("esempio.csv");
//        esempioCSV.scriviCSV();
//        esempioCSV.leggiCSV("mieiAutori.csv");

//        EsempioExcel esempioExcel = new EsempioExcel();
//        esempioExcel.leggiExcel();
//        esempioExcel.scriviIlMioFileExcel();

        EsempioPDF esempioPDF = new EsempioPDF();
        esempioPDF.creaMioPdf();

//        EsempioJSON esempioJSON = new EsempioJSON();
//        JSONObject jo = esempioJSON.JSONOggetto();
//        logger.info("JSON Ogject "+ jo);
//        JSONArray ja = esempioJSON.JSONArray();
//        logger.info("JSON Array "+ ja);

    }
}
