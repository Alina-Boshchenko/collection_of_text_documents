package org.example.entities.impl;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import org.example.entities.interfaces.Opening;

import java.io.IOException;
import java.nio.file.Path;

public class Pdf implements Opening {
    @Override
    public String open(Path path) {
        String text = "";
        try {
            //конструктор по имени файла
            PdfReader reader = new PdfReader(path.toString());
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
}
