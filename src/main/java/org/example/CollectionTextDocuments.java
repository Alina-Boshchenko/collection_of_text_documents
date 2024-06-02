package org.example;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;

public class CollectionTextDocuments {

    private Map<String, File> files;
    private File firstFile;

    private final File firstFileDefault = new File("C:\\");

    private StringBuffer stringBuffer;

    private String indent;

    private int pageNumber = 1;


    public CollectionTextDocuments(File firstFile) {
        files = new HashMap<>();
        indent = "";
        stringBuffer = new StringBuffer("Коллекция документов:\n");
        this.firstFile = firstFile;
        fileCrawling(firstFile, files, stringBuffer, indent);
    }

    public CollectionTextDocuments() {
        files = new HashMap<>();
        indent = "";
        stringBuffer = new StringBuffer("Коллекция документов:\n");
        fileCrawling(firstFileDefault, files, stringBuffer, indent);
    }


    private void fileCrawling(File firstFile, Map<String, File> files, StringBuffer strBuff, String indent) {
        if (firstFile.isDirectory()) {
            File[] filesOfDirectory = firstFile.listFiles();
            if (filesOfDirectory != null) {
                for (File file : filesOfDirectory) {
                    if (file.isDirectory()) {
                        indent += "   ";
                        strBuff.append(indent + "Папка: " + file.getName() + "\n");
                        fileCrawling(file, files, strBuff, indent);
                    } else {
                        strBuff.append(indent + "Файл: " + file.getName() + "\n");
                        files.put(file.getName(), file);
                    }
                }
            }
        } else files.put(firstFile.getName(), firstFile);
    }


    public StringBuffer checkCollection() {
        return stringBuffer;
    }

    public void checkAbsoluteFileOfList() {
        for (String string : files.keySet()) {
            System.out.println(string);
        }
    }


    public String openFileForName(String nameFile) {
        if (nameFile.contains(".pdf")) {
            return openFilePdf(nameFile);
        } else if (nameFile.contains(".txt")) {
            return openFileTxt(nameFile);
        } else if (nameFile.contains(".png")) {
            return null;
        } else if (nameFile.contains(".docx")) {
            return openFileDocx(nameFile);
        } return nameFile;
    }

    private String openFilePdf(String nameFile) {
        String text = "";
        try {
            //конструктор по имени файла
            PdfReader reader = new PdfReader(files.get(nameFile).getAbsolutePath());
            for (int i = 1; i <= reader.getNumberOfPages(); ++i) { // i - номер страницы
                TextExtractionStrategy strategy = new SimpleTextExtractionStrategy(); //Создает новый инструмент визуализации извлечения текста.
                text += PdfTextExtractor.getTextFromPage(reader, i, strategy);  // Извлекает текст из PDF-файла. Извлеките текст с указанной страницы, используя стратегию извлечения.
            }
            reader.close();
        } catch (IOException e) {
            System.err.println(e);
        }
        return text;
    }

    private String openFileTxt(String nameFile) {
        StringJoiner strJ = new StringJoiner("\n");
        try (Scanner scanner = new Scanner(Paths.get(files.get(nameFile).getAbsolutePath()),"UTF-8")) {
            while (scanner.hasNextLine()) {
                strJ.add(scanner.nextLine());
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        return strJ.toString();
    }

    private String openFileDocx(String nameFile){
        String text = "";
        try {
            XWPFDocument docxFile = new XWPFDocument(OPCPackage.open(files.get(nameFile)));
            XWPFWordExtractor extractor = new XWPFWordExtractor(docxFile);
            text = extractor.getText();
        } catch (IOException | InvalidFormatException e) {
            System.err.println("Ошибка"+e);
        }
        return text;
    }

    public void documentSearch(String nameFile, String word){


    }
}


