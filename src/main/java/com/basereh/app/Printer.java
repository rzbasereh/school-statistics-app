package com.basereh.app;

import java.util.ArrayList;
import java.util.List;

public class Printer {
    public void print(CSV csv) {
        int[] maxLengths = new int[csv.getHeaders().size()];
        List<List<String>> records = new ArrayList<>();
        records.add(csv.getHeaders());
        records.addAll(csv.getRows());

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
        System.out.println(tableBuilder);
    }
}
