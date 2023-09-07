package com.basereh.app;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class CLI {
    private final CSVParser csvParser;

    private final StatisticsFacade statisticsFacade;

    private final Printer printer;

    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void printOptions() {
        System.out.println("\nPlease select one of these options:");
        System.out.println("\t[1] Parse CSV formatted string");
        System.out.println("\t[2] Parse CSV formatted string (Typed by Enter)");
        System.out.println("\t[3] School statistics");
        System.out.println("\t[4] School statistics (optional on statistic measurement method)");
    }

    private void CSVParserOption(Scanner scanner) {
        String str = scanner.next();
        try {
            CSV csv = csvParser.parse(str);
            printer.print(csv);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void CSVLineParserOption(Scanner scanner) {
        System.out.print("Enter number of rows: ");
        int rowLength = scanner.nextInt();

        List<String> lines = new ArrayList<>();
        for (int i = 0; i < rowLength; i++) {
            lines.add(scanner.next());
        }

        try {
            CSV csv = csvParser.parse(lines);
            System.out.println(csv);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void calculateSchoolStatistics(Scanner scanner) {
//        System.out.print("Enter your file path: ");
//        String filePath = scanner.next();
        List<String> headers = Arrays.asList("name", "school", "grade", "className", "score");
        try {
//            CSV csv = csvParser.parseFile(filePath);
            CSV csv = new CSV(headers);
            csv.addRow(Arrays.asList("ali", "sch", "G1", "C1", "10"));
            csv.addRow(Arrays.asList("akbar", "sch", "G2", "C1", "20"));

            StudentList studentList = new StudentList();
            studentList.extractFromCSV(csv);
            List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(studentList);
            printer.print(results);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isContinue(Scanner scanner) {
        System.out.print("\nAre you want to continue (Y/n): ");
        String res = scanner.next();
        return !res.equals("n");
    }

    public void run() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        do {
            clear();
            printOptions();

            int selectedOption = scanner.nextInt();
            switch (selectedOption) {
                case 1:
                    CSVParserOption(scanner);
                    break;
                case 2:
                    CSVLineParserOption(scanner);
                    break;
                case 3:
                    calculateSchoolStatistics(scanner);
                    break;
                case 4:
                    calculateSchoolStatistics(scanner);
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        } while (isContinue(scanner));

        scanner.close();
    }
}
