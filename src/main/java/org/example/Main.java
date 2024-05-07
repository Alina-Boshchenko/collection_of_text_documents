package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<File> files = new ArrayList<>();
        fileCrawling(new File("C:\\"), files);
        for (File file: files){
            System.out.println(file.getAbsoluteFile());
        }
        System.out.println(files.size());
    }

    public static void fileCrawling(File firstFile, List<File> files){
        if (firstFile.isDirectory()){
//            System.out.println("Я тут"+firstFile.getAbsoluteFile());
            File[] filesOfDirectory = firstFile.listFiles();
            if (filesOfDirectory != null){
                for (File file: filesOfDirectory){
                    if(file.isDirectory()){
                        fileCrawling(file, files);
                    } else {
                        if (file.getName().toLowerCase().endsWith(".txt")){
                            files.add(file);
                        }
                    }
                }
            }
        }

    }
}