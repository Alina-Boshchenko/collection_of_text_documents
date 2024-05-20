package org.example;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class CollectionTextDocuments {

    private HashMap<String, File> files = new HashMap<>();
    private File firstFile;

    private final File firstFileDefault = new File("C:\\");

    public CollectionTextDocuments(File firstFile) {
        this.firstFile = firstFile;
        fileCrawling(firstFile, files);
    }

    public CollectionTextDocuments() {
        fileCrawling(firstFileDefault, files);
    }

    private void fileCrawling(File firstFile, HashMap<String, File> files){
        if (firstFile.isDirectory()){
            File[] filesOfDirectory = firstFile.listFiles();
            if (filesOfDirectory != null){
                for (File file: filesOfDirectory){
                    if(file.isDirectory()){
                        fileCrawling(file, files);
                    } else {
                        if (file.getName().toLowerCase().endsWith(".txt")){
                            files.put(file.getName(), file);
                        }
                    }
                }
            }
        }
    }
    public void checkAbsoluteFileOfList(){
        for (String string : files.keySet()) {
            System.out.println(string);
        }
    }
    public void openFileForName(String name){
       File file = files.get(name);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()){
                System.out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
