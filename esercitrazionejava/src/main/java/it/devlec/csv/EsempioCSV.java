package it.devlec.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EsempioCSV {
    private static final Logger logger = LogManager.getLogger(EsempioCSV.class);
    private String[] INTESTAZIONE = {"autore", "titolo"};
    private Map<String, String> MIEI_AUTORI = new HashMap<>() {
        {
            put("Dante Aligheri", "La divina commedia");
            put("Giacomo Leopardi", "L'infinito");
            put("Eugenio Montale", "Ossi di seppia");
        }
    };
    public EsempioCSV() {
    }
    public void leggiCSV() {

        // cerca esempio.csv e ne salva l'url in mieiAutoriCSVPath
        String mieiAutoriCSVPath = null;
        try {
            mieiAutoriCSVPath = Paths.get(ClassLoader.getSystemResource("esempio.csv")
                    .toURI()).toString();
            logger.info("leggiCSV - Trovato file esempio.csv in: " + mieiAutoriCSVPath);
        } catch (URISyntaxException e) {
            logger.error("Errore nel trovare nel creare il file");
        }

        // legge esempio.csv e ne stampa le tuple leggendo da un iterator ricavato con il parse di un Reader
        Reader in = null;
        try {
            in = new FileReader(mieiAutoriCSVPath);
            // Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setSkipHeaderRecord(true)
                    .setHeader(INTESTAZIONE).build().parse(in);
            for (CSVRecord record : records) {
                String autore = record.get(INTESTAZIONE[0]);
                logger.info("Autore: " + autore);
                String titolo = record.get(1);
                logger.warn("Titolo: " + titolo);
            }
        } catch (IOException e) {
            logger.error("Si è verificato un errore", e);
        }
    }
    public void scriviCSV(){
        try {

            // cerca esempio.csv e ne salva l'url in mieiAutoriCSVPath
            String mieiAutoriCSVPath = null;
            try {
                mieiAutoriCSVPath = Paths.get(ClassLoader.getSystemResource("esempio.csv")
                        .toURI()).toString();
                logger.info("scriviCSV - Trovato file esempio.csv in: " + mieiAutoriCSVPath);
            } catch (URISyntaxException e) {
                logger.error("Errore nel trovare nel creare il file");
            }

            // elinina mieiAutori.csv (se esiste) dalla cartella in cui si trova esempio.csv
            File parent = new File(mieiAutoriCSVPath).getParentFile();
            String csvFile = parent.getAbsolutePath() + File.separator + "mieiAutori.csv";
            File mioCSV = new File(csvFile);
            if(mioCSV.exists()){
                logger.debug("Elimino il vecchio CSV");
                mioCSV.delete();
            }

            // prova a creare il file mieiAutori.csv
            boolean fileCreato = mioCSV.createNewFile();
            if(fileCreato){
                logger.debug("File CSV creato correttamente: " + csvFile);
            }

            // stampa i record del CSV iterando per ogni entry della mappa e stampando key,value
            FileWriter out = new FileWriter(mieiAutoriCSVPath);
            try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
                MIEI_AUTORI.forEach((author, title) -> {
                    try {
                        printer.printRecord(author, title);
                    } catch (IOException e) {
                        logger.error("Si è verificato un errore nel scrivere i miei autori", e);                    }
                });
            }

        } catch (IOException e) {
            logger.error("Si è verificato un errore", e);
        }
    }
}
