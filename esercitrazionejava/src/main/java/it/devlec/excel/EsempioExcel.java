package it.devlec.excel;

import it.devlec.csv.Libro;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class EsempioExcel {
    private static final Logger logger = LogManager.getLogger(EsempioExcel.class);

    public void leggiExcel() {
        logger.debug("Avvio lettura file excel");

        // cerca excel.xlsx e ne salva l'url nella stringa excelXLSXPath
        String excelXLSXPath = null;
        try {
            excelXLSXPath = Paths.get(ClassLoader.getSystemResource("excel.xlsx")
                    .toURI()).toString();
            logger.debug("leggiExcel - Trovato file " + excelXLSXPath);
        } catch (URISyntaxException e) {
            logger.error("Errore nel trovare nel creare il file");
        }

        // genera un FileInputStream a partire dal path di tipo String
        // genera un Workbook a partire dal FileInputStream
        FileInputStream fInpStream = null;
        try {
            fInpStream = new FileInputStream(excelXLSXPath);
            Workbook workbook = new XSSFWorkbook(fInpStream);
            Sheet sheet = workbook.getSheetAt(0);

            // stampa nei log
            int nRiga = 0;
            String riga = "";
            int nColonna = 0;

            for (Row row : sheet) {
                for (Cell cell : row) {
                    riga += "\tCella#" + (nColonna++) + "= " + cell.getStringCellValue();
                }
                nColonna = 0;
                logger.info("Riga#" + (nRiga++) + " " + riga);
                riga = "";
            }
            workbook.close();
            IOUtils.closeQuietly(fInpStream);
        } catch (IOException e) {
            logger.error("Errore nel leggere il mio excel", e);
        }
    }

    public void scriviIlMioFileExcel() {
        ArrayList<Persona> PERSONE = new ArrayList<>() {
            {
                add(new Persona("Andy", "Weir","43"));
                add(new Persona("Dante", "Aligheri","34"));
                add(new Persona("Giacomo", "Leopardi","45"));
                add(new Persona("Eugenio", "Montale","51"));
            }
        };

        // cerca excel.xlsx e ne salva l'url in excelFile
        String excelFile = null;
        try {
            excelFile = Paths.get(ClassLoader.getSystemResource("excel.xlsx")
                            .toURI()).toString();
        } catch (URISyntaxException e) {
            logger.error("Errore nel trovare nel creare il file");
        }

        // crea un foglio nel workbook e lo personalizza
        File parent = new File(excelFile).getParentFile();
        String mioExcel = parent.getAbsolutePath() + File.separator + "mioExcelGenerato.xlsx";
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Persona");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);


        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.CORAL.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        headerStyle.setFont(font);


        Cell headerCell = null;

        headerCell = header.createCell(0);
        headerCell.setCellValue("Nome");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Cognome");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Eta");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        int nRow = 1;
        Row row = null;
        for(var persona : PERSONE) {
            row = sheet.createRow(nRow++);
            row.createCell(0).setCellValue(persona.getNome());
            row.createCell(1).setCellValue(persona.getCognome());
            row.createCell(2).setCellValue(persona.getEta());
        }


//        Cell cell = row.createCell(0);
//        cell.setCellValue("Mario Rossi");
//        cell.setCellStyle(style);
//
//        cell = row.createCell(1);
//        cell.setCellValue(20);
//        cell.setCellStyle(style);


        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(mioExcel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(outputStream);
    }
}
