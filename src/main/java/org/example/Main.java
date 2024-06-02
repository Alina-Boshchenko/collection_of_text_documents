package org.example;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        CollectionTextDocuments c = new CollectionTextDocuments(new File("catalog_for_the_test"));

        System.out.println(c.checkCollection()); // дерево каталогов

        System.out.println("------------------ Файл PDF -------------------------");
        System.out.println(c.openFileForName("ispolzovanie-dereviev.pdf"));

        System.out.println("------------------ Файл TXT -------------------------");
        System.out.println(c.openFileForName("ascii.txt"));

        System.out.println("------------------ Файл DOCX ------------------------");
        System.out.println(c.openFileForName("Доклад.docx"));

        System.out.println("-----------------------------------------------------");

    }
}