package com.basereh.app;

import java.lang.String;
import java.util.Arrays;
import java.util.List;

public class CSVParser {

    public List<String> parseLine(String line) {
       return Arrays.asList(line.split(","));
    }

    public List<String> splitRows(String formatedString) {
        return Arrays.asList(formatedString.split(";"));
    }

    public CSV parse(List<String> lines) {
        CSV csv = new CSV();
        for (int i = 0; i < lines.size(); i++) {
            List<String> parsedLine = parseLine(lines.get(i));

            if (i == 0) {
                csv.setHeaders(parsedLine);
            } else {
                csv.addRow(parsedLine);
            }
        }
        return csv;
    }

    public CSV parse(String formatedString) {
        List<String> splitRows = splitRows(formatedString);
        return parse(splitRows);
    }
}
