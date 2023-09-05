package com.basereh.app;

import java.util.Scanner;

public class OptionManager {

    private void printOptions() {
        System.out.println("\nPlease select one of these options:");
        System.out.println("\t[1] Parse CSV formatted string");
        System.out.println("\t[2] Parse CSV formatted string (Typed by Enter)");
    }

    private void printCSV(String[][] parsedRows) {
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

    private void CSVParserOption(Scanner scanner) {
        String str = scanner.next();
        CSVParser parser = new CSVParser();
        String[][] parsedCSV = parser.parse(str);
        printCSV(parsedCSV);
    }

    private void CSVLineParserOption(Scanner scanner) {
        System.out.print("Enter number of rows: ");
        int rowLength = scanner.nextInt();
        String[] lines = new String[rowLength];
        for (int i = 0; i < rowLength; i++) {
            lines[i] = scanner.next();
        }

        CSVParser parser = new CSVParser();
        String[][] parsedCSV = parser.parse(String.join(";", lines));
        printCSV(parsedCSV);
    }

    private void manageOptions() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        int selectedOption = scanner.nextInt();
        switch (selectedOption) {
            case 1:
                CSVParserOption(scanner);
                break;
            case 2:
                CSVLineParserOption(scanner);
                break;
            default:
                System.out.println("Invalid option!");

        }
        scanner.close();
    }

    public void run() {
        printOptions();
        manageOptions();
    }
}
