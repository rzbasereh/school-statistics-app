package com.basereh.app;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
public class CLI {
    private final CSVParser csvParser;

    private final StatisticsFacade statisticsFacade;

    private final Printer printer;

    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private int selectOption(String title, List<String> options, Scanner scanner) throws Exception {
        System.out.println("\n" + title);
        for (int i = 0; i < options.size(); i++) {
            System.out.println("\t[" + (i + 1) + "] " + options.get(i));
        }

        int selectedOptionIndex = scanner.nextInt() - 1;
        if (selectedOptionIndex < 0 || selectedOptionIndex >= options.size()) {
            throw new Exception("Invalid option selected!");
        }
        return selectedOptionIndex;
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

    private void calculateSchoolStatisticsByMeasurementMethod(Scanner scanner) throws Exception {
        List<String> methods = statisticsFacade.getMeasurementMethods().stream().map(Class::getSimpleName).toList();
        int selectedMethodIndex = selectOption("Please choose one of this methods:", methods, scanner);
        String selectedMethod = methods.get(selectedMethodIndex);

        List<String> headers = Arrays.asList("name", "school", "grade", "className", "score");
        try {
            CSV csv = new CSV(headers);
            csv.addRow(Arrays.asList("ali", "sch", "G1", "C1", "10"));
            csv.addRow(Arrays.asList("akbar", "sch", "G2", "C1", "20"));

            StudentList studentList = new StudentList();
            studentList.extractFromCSV(csv);
            List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(studentList, selectedMethod);
            printer.print(results);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void calculateSchoolStatisticsByMeasurementMethodAndStatisticTarget(Scanner scanner) throws Exception {
        List<String> methods = statisticsFacade.getMeasurementMethods().stream().map(Class::getSimpleName).toList();
        int selectedMethodIndex = selectOption("Please choose one of this methods:", methods, scanner);

        List<StatisticTarget> statisticTargets = statisticsFacade.getStatisticTargets();
        int selectedTargetIndex = selectOption("Please choose one of this targets:", statisticTargets.stream().map(StatisticTarget::name).toList(), scanner);

        String selectedMethod = methods.get(selectedMethodIndex);
        StatisticTarget selectedTarget = statisticTargets.get(selectedTargetIndex);

        List<String> headers = Arrays.asList("name", "school", "grade", "className", "score");
        try {
            CSV csv = new CSV(headers);
            csv.addRow(Arrays.asList("ali", "sch", "G1", "C1", "10"));
            csv.addRow(Arrays.asList("akbar", "sch", "G2", "C1", "20"));

            StudentList studentList = new StudentList();
            studentList.extractFromCSV(csv);
            List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(
                    studentList,
                    Collections.singletonList(selectedMethod),
                    selectedTarget
            );
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
        List<String> options = Arrays.asList(
                "Parse CSV formatted string",
                "Parse CSV formatted string (Typed by Enter)",
                "School statistics",
                "School statistics (choose a statistic measurement method)",
                "School statistics (choose a statistic measurement method and type of statistic target)"
        );

        do {
            clear();
            try {
                switch (selectOption("Please select one of these options:", options, scanner)) {
                    case 0 -> CSVParserOption(scanner);
                    case 1 -> CSVLineParserOption(scanner);
                    case 2 -> calculateSchoolStatistics(scanner);
                    case 3 -> calculateSchoolStatisticsByMeasurementMethod(scanner);
                    case 4 -> calculateSchoolStatisticsByMeasurementMethodAndStatisticTarget(scanner);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue(scanner));

        scanner.close();
    }
}
