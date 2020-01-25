package com.example.springintro.util;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileUtil {

    String[] fileContent(String path) throws IOException;

}
