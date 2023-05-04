package it.devlec.excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class EsempioExcel {
    private static final Logger logger = LogManager.getLogger(EsempioExcel.class);

    public void leggiExcel() {
        logger.debug("Provo a leggere un file excel");
        String excelDiProva = null;
        try {
            excelDiProva = Paths.get(ClassLoader.getSystemResource("excel.xlsx")
                    .toURI()).toString();
        } catch (URISyntaxException e) {
            logger.error("Errore nel trovare nel creare il file");
        }
        FileInputStream file = null;
        try {
            file = new FileInputStream(excelDiProva);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            int i = 0;
            for (Row row : sheet) {
                for (Cell cell : row) {
                    logger.info("Valore " + cell.getStringCellValue());
                }
            }
            workbook.close();
            IOUtils.closeQuietly(file);
        } catch (IOException e) {
            logger.error("Errore nel leggere il mio excel", e);
        }
    }

    public void scriviIlMioFileExcel() {
        String excelFile = null;
        try {
            excelFile = Paths.get(ClassLoader.getSystemResource("excel.xlsx")
                            .toURI()).toString();
        } catch (URISyntaxException e) {
            logger.error("Errore nel trovare nel creare il file");
        }
        File parent = new File(excelFile).getParentFile();
        String mioExcel = parent.getAbsolutePath() + File.separator + "mioExcelGenerato.xlsx";
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Persona");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Nome");
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(1);
        headerCell.setCellValue("Eta");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        Row row = sheet.createRow(2);
        Cell cell = row.createCell(0);
        cell.setCellValue("Mario Rossi");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(20);
        cell.setCellStyle(style);
        File currDir = new File(".");


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
