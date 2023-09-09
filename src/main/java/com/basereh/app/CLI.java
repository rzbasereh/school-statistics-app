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

    public void mainLoop() {
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
                    case 0 -> {
                        String str = scanner.next();
                        CSV csv = csvParser.parse(str);
                        printer.print(csv);
                    }
                    case 1 -> {
                        List<String> lines = readStringLineByLine();
                        CSV csv = csvParser.parse(lines);
                        printer.print(csv);
                    }
                    case 2 -> {
                        List<Student> students = getStudentsFromCSVFile();
                        List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(students);
                        printer.print(results);
                    }
                    case 3 -> {
                        List<Student> students = getStudentsFromCSVFile();
                        Class<? extends StatisticCalculator> selectedCalculator = selectStatisticCalculator();
                        List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(
                                students,
                                selectedCalculator
                        );
                        printer.print(results);
                    }
                    case 4 -> {
                        List<Student> students = getStudentsFromCSVFile();
                        Class<? extends StatisticCalculator> selectedCalculator = selectStatisticCalculator();
                        Class<? extends ScoreCollector> selectedCollector = selectScoreCollector();
                        List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(
                                students,
                                Collections.singletonList(selectedCalculator),
                                Collections.singletonList(selectedCollector)
                        );
                        printer.print(results);
                    }
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

    private List<String> readStringLineByLine() {
        System.out.print("Enter number of lines: ");
        int rowLength = scanner.nextInt();

        List<String> lines = new ArrayList<>();
        for (int i = 0; i < rowLength; i++) {
            lines.add(scanner.next());
        }
        return lines;
    }

    private List<Student> getStudentsFromCSVFile() {
        System.out.print("Enter your file path: ");
        String filePath = scanner.next();
        CSVToStudentExtractor csvToStudentExtractor = new CSVToStudentExtractor();
        List<Student> students = new ArrayList<>();
        try {
            CSV csv = csvParser.parseFile(filePath);
            students = csvToStudentExtractor.extract(csv);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return students;
    }

    private Class<? extends StatisticCalculator> selectStatisticCalculator() throws Exception {
        List<Class<? extends StatisticCalculator>> calculators = statisticsFacade.getStatisticCalculators();
        int selectedOptionIndex = selectOption(
                "Please choose one of this methods:",
                calculators.stream().map(Class::getName).toList()
        );
        return calculators.get(selectedOptionIndex);
    }

    private Class<? extends ScoreCollector> selectScoreCollector() throws Exception {
        List<Class<? extends ScoreCollector>> collectors = statisticsFacade.getScoreCollectors();
        int selectedCollectorIndex = selectOption(
                "Please choose one of this targets:",
                collectors.stream().map(Class::getName).toList()
        );
        return collectors.get(selectedCollectorIndex);
    }

    private boolean isContinue() {
        System.out.print("\nAre you want to continue (Y/n): ");
        String res = scanner.next();
        return !res.equals("n");
    }
}
