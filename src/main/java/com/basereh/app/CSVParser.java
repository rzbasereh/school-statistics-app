package com.basereh.app;

import java.io.*;
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CSVParser {

    public List<String> parseLine(String line) {
        return Arrays.asList(line.split(","));
    }

    public List<String> splitRows(String formatedString) {
        return Arrays.asList(formatedString.split(";"));
    }

    public CSV parse(List<String> lines) throws IOException {
        CSV csv = new CSV();
        int columnLength = 0;
        for (int i = 0; i < lines.size(); i++) {
            List<String> parsedLine = parseLine(lines.get(i));

            if (i == 0) {
                columnLength = parsedLine.size();
                csv.setHeaders(parsedLine);
            } else {
                if (parsedLine.size() != columnLength) {
                    throw new IOException("Invalid CSV format");
                }
                csv.addRow(parsedLine);
            }
        }
        return csv;
    }

    public CSV parse(String formatedString) throws IOException {
        List<String> splitRows = splitRows(formatedString);
        return parse(splitRows);
    }

    public CSV parseFile(String filePath) throws IOException {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            return parse(lines);
        }
    }
}
