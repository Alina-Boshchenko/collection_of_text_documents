package org.example.enums;

public enum FolderType {

    DEFAULT_FOLDER("C:\\");
     private String value;

    FolderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
