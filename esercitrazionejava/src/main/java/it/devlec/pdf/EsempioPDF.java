package it.devlec.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class EsempioPDF {
    private static final Logger logger =  LogManager.getLogger(EsempioPDF.class);

    public EsempioPDF() {
    }

    public void creaMioPdf(){
        Document document = new Document();

        // cerca excel.xlsx e ne salva l'url nella stringa excelXLSXPath
        try {
            String excelXLSXPath = null;
            try {
                excelXLSXPath = Paths.get(ClassLoader.getSystemResource("excel.xlsx")
                        .toURI()).toString();
                logger.debug("creaMioPdf - Trovato file " + excelXLSXPath);
            } catch (URISyntaxException e) {
                logger.error("Errore nel trovare nel creare il file");
            }
            File parent = new File(excelXLSXPath).getParentFile();
            String mioPDFPath = parent.getAbsolutePath() + File.separator + "mioPdf.pdf";

            FileOutputStream fileOutputStream = new FileOutputStream(mioPDFPath);
            PdfWriter.getInstance(document,fileOutputStream);

            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 30, BaseColor.BLACK);
            Chunk chunk = new Chunk("Dipendenti licenziati", font);
            document.add(chunk);

            Phrase p = new Phrase(new Chunk(new VerticalPositionMark()));
            document.add(p);

            // creazione nuova tabella
            PdfPTable table = new PdfPTable(3);
            addTableHeader(table);
            addRow(table, "Mario", "Rossi", "32");
            addRow(table, "Alberto", "Russo", "54");
            addCustomRow(table, "Alberto", "Angela", "19");
            document.add(table);

            document.add(p);

            Path path = Paths.get(ClassLoader.getSystemResource("jasonJSON.jpg").toURI());
            Image img = Image.getInstance(path.toAbsolutePath().toString());
            img.scalePercent(10);
            img.setAlignment(1);

            document.add(img);

            document.close();

        } catch (DocumentException  | IOException |
                URISyntaxException e) {
            logger.error("Errore nella creazione del mio PDF", e);
        }

    }
    private void addTableHeader(PdfPTable table) {
        Stream.of("Nome", "Cognome", "Età")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.ORANGE);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRow(PdfPTable table, String cell1, String cell2, String cell3) {
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
    }
    private void addCustomRow(PdfPTable table, String cell1, String cell2, String cell3)
            throws URISyntaxException, BadElementException, IOException {

        PdfPCell topAlignCell = new PdfPCell(new Phrase(cell1));
        topAlignCell.setHorizontalAlignment(Element.ALIGN_TOP);
        table.addCell(topAlignCell);

        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase(cell2));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);

        PdfPCell verticalAlignCell = new PdfPCell(new Phrase(cell3));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);
    }
}
