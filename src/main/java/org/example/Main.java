package org.example;

import org.example.impl.CollectionTextDocuments;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        CollectionTextDocuments c = new CollectionTextDocuments();
        c.traversingDirectoryTree("catalog_for_the_test");

    }
}