package org.example.entities.interfaces;

import java.nio.file.Path;

public interface Replacing {

    Path replace(Path path, String oldValue, String newValue);

}
