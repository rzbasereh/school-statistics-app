package com.basereh.app;

import java.io.*;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CSVParser {

    public List<String> parseLine(String line) {
        return Arrays.asList(line.split(","));
    }

    public List<String> splitRows(String formatedString) {
        return Arrays.asList(formatedString.split(";"));
    }

    public CSV parse(List<String> lines) throws IOException {
        List<String> header = parseLine(lines.remove(0));
        CSV csv = new CSV(header);
        for (var line : lines) {
            List<String> parsedLine = parseLine(line);
            csv.addRow(parsedLine);
        }
        return csv;
    }

    public CSV parse(String formatedString) throws IOException {
        List<String> splitRows = splitRows(formatedString);
        return parse(splitRows);
    }

    public CSV parseFile(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            return parse(lines.toList());
        }
    }
}
