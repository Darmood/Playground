package it.devlec.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EsempioCSV {
    private static final Logger logger = LogManager.getLogger(EsempioCSV.class);
    private String[] INTESTAZIONE = {"autore", "titolo"};
    private ArrayList<Libro> MIEI_AUTORI = new ArrayList<>() {
        {
            add(new Libro("Andy Weir", "The Martian","2011"));
            add(new Libro("Dante Aligheri", "La divina commedia","1321"));
            add(new Libro("Giacomo Leopardi", "L'infinito","1826"));
            add(new Libro("Eugenio Montale", "Ossi di seppia","1925"));
        }
    };
    public EsempioCSV() {
    }
    public void leggiCSV(String fileName) {

        // cerca esempio.csv e ne salva l'url in CSVPath
        String CSVPath = null;
        try {
            CSVPath = Paths.get(ClassLoader.getSystemResource(fileName)
                    .toURI()).toString();
            logger.debug("leggiCSV - Trovato file esempio.csv in: " + CSVPath);
        } catch (URISyntaxException e) {
            logger.error("Errore nel trovare nel creare il file");
        }

        // legge esempio.csv e ne stampa le tuple leggendo da un iterator ricavato con il parse di un Reader
        Reader in = null;
        try {
            in = new FileReader(CSVPath);
            // Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setSkipHeaderRecord(false)
                    .setHeader(INTESTAZIONE).build().parse(in);
            int indice = 0;
            for (CSVRecord record : records) {
                String autore = record.get(INTESTAZIONE[0]);
                String titolo = record.get(1);
                logger.info("Lettura riga #" + (indice++) + "\tAutore:" + autore + "\t Titolo:" + titolo);
            }
        } catch (IOException e) {
            logger.error("Si è verificato un errore", e);
        }
    }
    public void scriviCSV(){
        try {

            // cerca esempio.csv e ne salva l'url in esempioCSVPath
            String esempioCSVPath = null;
            try {
                esempioCSVPath = Paths.get(ClassLoader.getSystemResource("esempio.csv")
                        .toURI()).toString();
                logger.debug("scriviCSV - Trovato file esempio.csv in: " + esempioCSVPath);
            } catch (URISyntaxException e) {
                logger.error("Errore nel trovare nel creare il file");
            }

            // elinina mieiAutori.csv (se esiste) dalla cartella in cui si trova esempio.csv
            File parent = new File(esempioCSVPath).getParentFile();
            String mieiAutoriCSVPath = parent.getAbsolutePath() + File.separator + "mieiAutori.csv";
            File mioCSVFile = new File(mieiAutoriCSVPath);
            if(mioCSVFile.exists()){
                logger.info("Elimino il vecchio CSV " + mieiAutoriCSVPath);
                mioCSVFile.delete();
            }

            // prova a creare il file mieiAutori.csv
            boolean fileCreato = mioCSVFile.createNewFile();
            if(fileCreato){
                logger.info("File CSV creato correttamente: " + mieiAutoriCSVPath);
            }

            // stampa i record del CSV iterando per ogni entry della mappa e stampando key,value
            FileWriter out = new FileWriter(mieiAutoriCSVPath);
            try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
                MIEI_AUTORI.forEach((libro) -> {
                    try {
                        printer.printRecord(libro.getTitolo(), libro.getAutore(), libro.getAnnoPubblicazione());
                    } catch (IOException e) {
                        logger.error("Si è verificato un errore nel scrivere i miei autori", e);                    }
                });

//                MIEI_AUTORI.forEach((author, title) -> {
//                    try {
//                        printer.printRecord(author, title);
//                    } catch (IOException e) {
//                        logger.error("Si è verificato un errore nel scrivere i miei autori", e);                    }
//                });

            }

        } catch (IOException e) {
            logger.error("Si è verificato un errore", e);
        }
    }
}
