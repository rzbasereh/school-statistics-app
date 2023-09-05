package com.basereh.app;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CSV {
    private final List<String> headers;

    private List<List<String>> rows = new ArrayList<>();

    public void addRow(List<String> row) throws IOException {
        if (row.size() != headers.size()) {
            throw new IOException("Invalid CSV format");
        }
        rows.add(row);
    }

    public String toString() {
        int[] maxLengths = new int[headers.size()];
        List<List<String>> records = new ArrayList<>();
        records.add(headers);
        records.addAll(rows);

        for (List<String> record : records) {
            for (int i = 0; i < record.size(); i++) {
                maxLengths[i] = Math.max(maxLengths[i], record.get(i).length());
            }
        }
        StringBuilder formatBuilder = new StringBuilder();
        for (int i = 0; i < maxLengths.length; i++) {
            formatBuilder.append("|%-").append(maxLengths[i] + 2).append("s");
        }
        String format = formatBuilder.toString();

        StringBuilder tableBuilder = new StringBuilder();
        for (List<String> record : records) {
            tableBuilder.append(String.format(format, record.toArray())).append("\n");
        }
        return tableBuilder.toString();
    }
}
