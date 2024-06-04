package org.example.enums;

public enum Сatalog {

    FIRST_FILE_DEFAULT("C:\\");
     private String value;

    Сatalog(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
