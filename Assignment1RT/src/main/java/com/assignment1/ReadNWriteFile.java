package com.assignment1;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static java.lang.System.out;


public class ReadNWriteFile {


    public static void readData() {

        out.println("Reading the data from... " + Common.URL);
        out.println(" ");

        try {

            Document doc = Jsoup.connect(Common.URL).get();

            Elements head = doc.select("span.mw-headline#Trivia");
            String head1 = head.text();
            out.println(head1);


            for (Element row : doc.select("tr[align]")) {

                Elements title = row.select("th");
                Elements info = row.select("td");

                String c1 = title.text();
                String c2 = info.text();

                Common.dataEntry.add(new Table(c1, c2));


                out.printf("%-50s %s%n", c1, c2);

            }
            out.println("\nSuccessfully read the data!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeData() {

        out.println("\nWriting the data to ExcelTrivia.xlsx");

        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Trivia");

        try {

            for (int i = 0; i < Common.dataEntry.size(); i++) {

                CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
                // cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
                //cellStyle.setFont(font);
                cellStyle.setWrapText(true);

                XSSFRow row = sheet.createRow(i);

                XSSFCell row1 = row.createCell(0);
                row1.setCellValue(Common.dataEntry.get(i).getTitle());
                sheet.autoSizeColumn(0);

                XSSFCell row2 = row.createCell(1);
                row2.setCellValue(Common.dataEntry.get(i).getInfo());
                sheet.autoSizeColumn(1);

            }

            FileOutputStream outFile = new FileOutputStream(new File("ExcelTrivia.xlsx"));
            workbook.write(outFile);
            outFile.flush();
            outFile.close();
            workbook.close();
            out.println("\nExcelTrivia.xlsx is written successfully on disk.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void openExcel() {
        try {
            out.print("\nOpening the file...");
            Desktop.getDesktop().open(new File("ExcelTrivia.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
