package org.example.impl;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.example.enums.Сatalog;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;

public class CollectionTextDocuments {
    private Map<String, File> files;
    private static final File FIRST_FILE_DEFAULT = new File(Сatalog.FIRST_FILE_DEFAULT.getValue());
    private static final String HEADING = "Коллекция документов:\n";
    private StringBuffer stringBuffer;
    private String indent;
    private int pageNumber;

// констр
    public CollectionTextDocuments() {
        files = new HashMap<>();
        indent = "";
        stringBuffer = new StringBuffer(HEADING);
        pageNumber = 1;
    }

    // обход с помощью пакета nio
    public void crawlingFilesUsingNIO() throws IOException {
        crawlingFilesNIO(FIRST_FILE_DEFAULT.toPath());
    }
    public void crawlingFilesUsingNIO(File firstFile) throws IOException {
        crawlingFilesNIO(firstFile.toPath());
    }
    private void crawlingFilesNIO(Path path) throws IOException {
        Files.walkFileTree(path, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                indent += " ";
                stringBuffer.append(String.format("%s Папка: %s\n",indent,dir.toFile().getName()));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                stringBuffer.append(String.format("%s Файл: %s\n",indent,file.toFile().getName()));
                files.put(file.toFile().getName(), file.toFile());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("Не удалось просмотреть файл");
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }

    // обход рекурсивный
    public void crawlingFilesWithRecursion(File firstFile){
        traversingFilesWithRecursion(firstFile, files, stringBuffer, indent);
    }
    public void crawlingFilesWithRecursion(){
        traversingFilesWithRecursion(FIRST_FILE_DEFAULT, files, stringBuffer, indent);
    }
    private void traversingFilesWithRecursion(File firstFile, Map<String, File> files, StringBuffer strBuff, String indent) {
        if (firstFile.isDirectory()) {
            File[] filesOfDirectory = firstFile.listFiles();
            if (filesOfDirectory != null) {
                for (File file : filesOfDirectory) {
                    if (file.isDirectory()) {
                        indent += "   ";
                        strBuff.append(String.format("%s Папка: %s\n",indent,file.getName()));
                        traversingFilesWithRecursion(file, files, strBuff, indent);
                    } else {
                        strBuff.append(String.format("%s Файл: %s\n",indent,file.getName()));
                        files.put(file.getName(), file);
                    }
                }
            }
        } else files.put(firstFile.getName(), firstFile);
    }



//показать дерево коллекции
    public StringBuffer checkCollection() {
        return stringBuffer;
    }

    public void checkAbsoluteFileOfList() {
        for (String string : files.keySet()) {
            System.out.println(string);
        }
    }





// вместо этого метода свич с выборкой по концу файла
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













// ---------------- сделаю интерфейс и отдельные реализации под каждый формат ----------------------- //

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



// replac - заменить
    // create - создать
    public void documentSearch(String nameFile, String word){


    }
}


