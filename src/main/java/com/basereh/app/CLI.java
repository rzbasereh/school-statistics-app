package com.basereh.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private final CSVParser csvParser;

    public CLI(CSVParser csvParser) {
        this.csvParser = csvParser;
    }

    private void printOptions() {
        System.out.println("\nPlease select one of these options:");
        System.out.println("\t[1] Parse CSV formatted string");
        System.out.println("\t[2] Parse CSV formatted string (Typed by Enter)");
    }

    private void CSVParserOption(Scanner scanner) {
        String str = scanner.next();
        CSV csv = csvParser.parse(str);
        System.out.println(csv);
    }

    private void CSVLineParserOption(Scanner scanner) {
        System.out.print("Enter number of rows: ");
        int rowLength = scanner.nextInt();

        List<String> lines = new ArrayList<>();
        for (int i = 0; i < rowLength; i++) {
            lines.add(scanner.next());
        }

        CSV csv = csvParser.parse(lines);
        System.out.println(csv);
    }

    private boolean isContinue(Scanner scanner) {
        System.out.print("Are you want to continue (Y/n): ");
        String res = scanner.next();
        return !res.equals("n");
    }

    public void run() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        do {
            printOptions();

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
        } while (isContinue(scanner));
        scanner.close();
    }
}
