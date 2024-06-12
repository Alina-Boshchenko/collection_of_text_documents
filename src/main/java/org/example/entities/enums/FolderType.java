package org.example.entities.enums;

public enum FolderType {

    DEFAULT_FOLDER("catalog_for_the_test");
     private String value;

    FolderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
