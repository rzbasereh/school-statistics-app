package com.basereh.app;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
public class CLI {
    private final Scanner scanner;

    private final CSVParser csvParser;

    private final StatisticsFacade statisticsFacade;

    private final Printer printer;

    public void run() {
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
                switch (selectOption("Please select one of these options:", options)) {
                    case 0 -> CSVParserOption();
                    case 1 -> CSVLineParserOption();
                    case 2 -> calculateSchoolStatisticsOption();
                    case 3 -> calculateSchoolStatisticsByMeasurementMethodOption();
                    case 4 -> calculateSchoolStatisticsByMeasurementMethodAndStatisticTargetOption();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue());
    }

    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private int selectOption(String title, List<String> options) throws Exception {
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

    private void CSVParserOption() {
        String str = scanner.next();
        try {
            CSV csv = csvParser.parse(str);
            printer.print(csv);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void CSVLineParserOption() {
        System.out.print("Enter number of rows: ");
        int rowLength = scanner.nextInt();

        List<String> lines = new ArrayList<>();
        for (int i = 0; i < rowLength; i++) {
            lines.add(scanner.next());
        }

        try {
            CSV csv = csvParser.parse(lines);
            printer.print(csv);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private StudentList getStudentListFromFile() {
        System.out.print("Enter your file path: ");
        String filePath = scanner.next();
        StudentList studentList = new StudentList();
        try {
            CSV csv = csvParser.parseFile(filePath);
            studentList.extractFromCSV(csv);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return studentList;
    }

    private void calculateSchoolStatisticsOption() {
        StudentList studentList = getStudentListFromFile();
        List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(studentList);
        printer.print(results);
    }

    private void calculateSchoolStatisticsByMeasurementMethodOption() throws Exception {
        StudentList studentList = getStudentListFromFile();

        List<Class<? extends StatisticCalculator>> methods = statisticsFacade.getMeasurementMethods();
        int selectedMethodIndex = selectOption(
                "Please choose one of this methods:",
                methods.stream().map(Class::getName).toList()
        );

        List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(
                studentList,
                methods.get(selectedMethodIndex)
        );
        printer.print(results);
    }

    private void calculateSchoolStatisticsByMeasurementMethodAndStatisticTargetOption() throws Exception {
        StudentList studentList = getStudentListFromFile();

        List<Class<? extends StatisticCalculator>> methods = statisticsFacade.getMeasurementMethods();
        int selectedMethodIndex = selectOption(
                "Please choose one of this methods:",
                methods.stream().map(Class::getName).toList()
        );

        List<Class<? extends ScoreCollector>> scoreCollectors = statisticsFacade.getScoreCollectors();
        int selectedTargetIndex = selectOption(
                "Please choose one of this targets:",
                scoreCollectors.stream().map(Class::getName).toList()
        );

        List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(
                studentList,
                Collections.singletonList(methods.get(selectedMethodIndex)),
                Collections.singletonList(scoreCollectors.get(selectedTargetIndex))
        );
        printer.print(results);
    }

    private boolean isContinue() {
        System.out.print("\nAre you want to continue (Y/n): ");
        String res = scanner.next();
        return !res.equals("n");
    }
}
