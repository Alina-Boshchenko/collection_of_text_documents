package org.example.impl;

import org.example.enums.FolderType;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;


public class CollectionTextDocuments {
    private List<Path> files;
    private static final Path DEFAULT_FOLDER = Paths.get(FolderType.DEFAULT_FOLDER.getValue());
    private static final String HEADING = "Коллекция документов:\n";
    private StringBuffer stringBuffer;
    private String indent;

    public CollectionTextDocuments() {
        files = new ArrayList<>();
        indent = "";
        stringBuffer = new StringBuffer(HEADING);
    }
    public void traversingDirectoryTree() {
        try {
            crawlingFilesNIO(DEFAULT_FOLDER);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    public void traversingDirectoryTree(String nameFile) {
        try {
            crawlingFilesNIO(Paths.get(nameFile));
        } catch (IOException e) {
            System.err.println(e);
        }
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
                files.add(file);
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
    public String collectionStructure() {
        return stringBuffer.toString();
    }

    public void printAbsolutePathOfList() {
        files.forEach(System.out::println);
    }



private Path searchForName(String nameFile){
        return files.stream().filter(el -> el.toFile().getName().equals(nameFile)).findFirst().get();
}

private String getFileExtension(String nameFile){
    int indexOfTheLastPoint = nameFile.lastIndexOf('.');
    if(indexOfTheLastPoint == -1){
        return "Расширение не определено";
    } return nameFile.substring(indexOfTheLastPoint);
}


    public String open(String nameFile) {
        Path path = searchForName(nameFile);
        String extension = getFileExtension(nameFile);
        switch (extension){
            case ".txt" -> {
                return new Txt().open(path);
            }
            case ".pdf" -> {
                return new Pdf().open(path);
            }
            case ".docx" -> {
                return new Docx().open(path);
            }
            default -> {
                return null;
            }
        }
    }
    public void search(String nameFile, String word){
        Path path = searchForName(nameFile);
        new Txt().search(path,word);
    }

    public boolean replace(String nameFile, String oldValue, String newValue){
        Path pathOld = searchForName(nameFile);
        Path pathNew = new Txt().replace(pathOld,oldValue,newValue);
        return files.add(pathNew);
    }

    public boolean createFile(String pathToTheFile) {
        Path pathNew = new Txt().createFile(pathToTheFile);
        return files.add(pathNew);
    }

    public void documentSize(String nameFile) {
        Path path = searchForName(nameFile);
        try {
            System.out.println(Files.getAttribute(path, "dos:size")+" байт");
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    public void documentOwner(String nameFile) {
        Path path = searchForName(nameFile);
        try {
            System.out.println(Files.getOwner(path).getName());
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    public void documentCreationTime(String nameFile) {
        Path path = searchForName(nameFile);
        try {
            System.out.println("Дата и время создания "+Files.getAttribute(path, "dos:creationTime"));
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public List<Path> sortingByOwner(){
           return files.stream().sorted((el1,el2) -> {
                try {
                    return Files.getOwner(el1).getName()
                            .compareTo(Files.getOwner(el2).getName());
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }).toList();
    }
    public List<Path> sortingBySize(){
        return files.stream().sorted((el1,el2) -> {
            try {
                if (Files.size(el1) > Files.size(el2)){
                    return 1;
                } else if (Files.size(el1)<Files.size(el2)) {
                    return -1;
                } else return 0;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }
    public List<Path> sortingByCreationTime(){
        return files.stream().sorted((el1,el2) -> {
            try {
                return Files.getAttribute(el1,"dos:creationTime").toString()
                        .compareTo(Files.getAttribute(el2,"dos:creationTime").toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }
    public int quantityOfDocuments(){
        return files.size();
    }


















}


