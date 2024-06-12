package org.example.entities.impl;

import org.example.entities.interfaces.Creating;
import org.example.entities.interfaces.Opening;
import org.example.entities.interfaces.Replacing;
import org.example.entities.interfaces.Searching;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;


public class Txt implements Opening, Searching, Replacing, Creating {
    @Override
    public String open(Path path){
        StringJoiner sJ = new StringJoiner("\n");
        try {
            Files.readAllLines(path).forEach(sJ::add); // List<String> list = Files.readAllLines(path);
            // list.forEach(sJ::add);
            return sJ.toString();
        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public void search(Path path, String word) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            String[] lines = new String(bytes).split("\n");
            int l = 1;
            for (String line : lines) {
                String[] words = line.split(" ");
                int w = 1;
                for (String wd: words) {
                    if (wd.equalsIgnoreCase(word)) {
                        System.out.printf("Найдено в %d-й строке, %d-е слово\n", l, w);
                    }
                    w++;
                }
                l++;
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Override
    public Path replace(Path path, String oldValue, String newValue) {
        Charset charset = StandardCharsets.UTF_8;
        try {
            String text = new String(Files.readAllBytes(path),charset);
            text = text.replaceAll(oldValue,newValue);
            Files.write(path, text.getBytes(StandardCharsets.UTF_8));
            return path;
        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public Path createFile(String pathToTheFile) {
        try {
            return Files.createFile(Paths.get(pathToTheFile));
        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }
}
