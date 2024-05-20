package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CollectionTextDocuments c = new CollectionTextDocuments(new File("C:/Users/Алина/Desktop/РГРТУ"));
        c.checkAbsoluteFileOfList();
        c.openFileForName("Результат агрохимического исследования.txt");
    }
}