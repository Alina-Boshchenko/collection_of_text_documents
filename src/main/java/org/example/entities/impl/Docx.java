package org.example.entities.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.example.entities.interfaces.Opening;

import java.io.IOException;
import java.nio.file.Path;

public class Docx implements Opening {
    @Override
    public String open(Path path) {
        String text = "";
        try {
            XWPFDocument docxFile = new XWPFDocument(OPCPackage.open(path.toString()));
            XWPFWordExtractor extractor = new XWPFWordExtractor(docxFile);
            text = extractor.getText();
        } catch (IOException | InvalidFormatException e) {
            System.err.println(e);
        }
        return text;
    }
}
