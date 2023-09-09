package com.basereh.app;

import java.io.*;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class CSVParser {

    public List<String> parseLine(String line) {
        return Arrays.asList(line.split(","));
    }

    public List<String> extractRows(String formatedString) {
        return Arrays.asList(formatedString.split(";"));
    }

    public CSV parse(List<String> lines) throws IOException {
        Iterator<String> iterator = lines.iterator();
        List<String> headers = parseLine(iterator.next());
        CSV csv = new CSV(headers);
        while (iterator.hasNext()) {
            var line = iterator.next();
            csv.addRow(parseLine(line));
        }
        return csv;
    }

    public CSV parse(String formatedString) throws IOException {
        List<String> records = extractRows(formatedString);
        return parse(records);
    }

    public CSV parseFile(String filePath) throws IOException {
        try (Stream<String> records = Files.lines(Path.of(filePath))) {
            return parse(records.toList());
        }
    }
}
