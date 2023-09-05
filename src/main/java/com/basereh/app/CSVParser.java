package com.basereh.app;

import java.lang.String;

import java.util.Arrays;

public class CSVParser {

    public String[][] parse(String formatedString) {
        String[] parsedLines = formatedString.split(";");
        return Arrays.stream(parsedLines).map(line -> line.split(",")).toArray(String[][]::new);
    }
}
