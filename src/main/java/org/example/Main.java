package org.example;

import org.example.impl.CollectionTextDocuments;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        CollectionTextDocuments c = new CollectionTextDocuments();

        try {
            c.crawlingFilesUsingNIO(new File("catalog_for_the_test")); // обход с помощью пакета nio
        } catch (IOException e) {
            System.err.printf("Ошибка %e",e);
        }

        System.out.println(c.checkCollection());
    }
}