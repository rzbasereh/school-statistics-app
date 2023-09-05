package com.basereh.app;

import java.lang.String;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CSVManager {

//    public static String read() {
//        Scanner scanner = new Scanner(System.in);
//        String result = scanner.next();
//        scanner.close();
//        return result;
//    }

//    public String[] readLineByLine() {
//        System.out.println("press ESC key to finish");
//        Scanner scanner = new Scanner(System.in);
//        ArrayList<String> result = new ArrayList<>();
//        while (scanner.hasNext()) {
//            result.add(scanner.next());
//        }
//        scanner.close();
//        return result.toArray(String[]::new);
//    }

    public static String[][] parser(String formatedString) {
        String[] parsedLines = formatedString.split(";");
        return Arrays.stream(parsedLines).map(line -> line.split(",")).toArray(String[][]::new);
    }

    public static void print(String[][] parsedRows) {
        int[] maxLengths = new int[parsedRows[0].length];

        for (int i = 0; i < parsedRows.length; i++) {
            for (String col : parsedRows[i]) {
                maxLengths[i] = Math.max(maxLengths[i], col.length());
            }
        }

        StringBuilder formatBuilder = new StringBuilder();
        for (int i = 0; i < maxLengths.length; i++) {
            formatBuilder.append("|%-").append(maxLengths[i] + 2).append("s");
        }
        String format = formatBuilder.toString();

        StringBuilder tableBuilder = new StringBuilder();
        for (String[] row : parsedRows) {
            tableBuilder.append(String.format(format, row)).append("\n");
        }
        System.out.println(tableBuilder);
    }
}
