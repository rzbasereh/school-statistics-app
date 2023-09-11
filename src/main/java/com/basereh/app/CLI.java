package com.basereh.app;

import com.basereh.app.CSVExtract.CSVToStudentExtractor;
import com.basereh.app.Domain.CSV;
import com.basereh.app.Domain.StatisticsResult;
import com.basereh.app.Domain.Student;
import com.basereh.app.Print.CSVPrinter;
import com.basereh.app.Print.StatisticResultPrinter;
import com.basereh.app.ScoreCollect.ScoreCollector;
import com.basereh.app.StatisticCalculate.StatisticCalculator;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class CLI {
    private final Scanner scanner;

    private final CSVParser csvParser;

    private final List<ScoreCollector> scoreCollectors;

    private final List<StatisticCalculator> statisticCalculators;

    private final StatisticsFacade statisticsFacade;

    private final CSVPrinter csvPrinter;

    private final StatisticResultPrinter statisticResultPrinter;

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
                        csvPrinter.print(csv);
                    }
                    case 1 -> {
                        List<String> lines = readStringLineByLine();
                        CSV csv = csvParser.parse(lines);
                        csvPrinter.print(csv);
                    }
                    case 2 -> {
                        List<Student> students = getStudentsFromCSVFile();
                        List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(
                                students,
                                statisticCalculators,
                                scoreCollectors
                        );
                        statisticResultPrinter.print(results);
                    }
                    case 3 -> {
                        List<Student> students = getStudentsFromCSVFile();
                        StatisticCalculator selectedCalculator = selectStatisticCalculator();
                        List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(
                                students,
                                Collections.singletonList(selectedCalculator),
                                scoreCollectors
                        );
                        statisticResultPrinter.print(results);
                    }
                    case 4 -> {
                        List<Student> students = getStudentsFromCSVFile();
                        StatisticCalculator selectedCalculator = selectStatisticCalculator();
                        ScoreCollector selectedCollector = selectScoreCollector();
                        List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(
                                students,
                                Collections.singletonList(selectedCalculator),
                                Collections.singletonList(selectedCollector)
                        );
                        statisticResultPrinter.print(results);
                    }
                }
            } catch (CLIException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue());
    }

    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private int selectOption(String title, List<String> options) throws CLIException {
        System.out.println("\n" + title);
        for (int i = 0; i < options.size(); i++) {
            System.out.println("\t[" + (i + 1) + "] " + options.get(i));
        }

        int selectedOptionIndex = scanner.nextInt() - 1;
        if (selectedOptionIndex < 0 || selectedOptionIndex >= options.size()) {
            throw new CLIException("Invalid option selected!");
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

    private List<Student> getStudentsFromCSVFile() throws CLIException {
        System.out.print("Enter your file path: ");
        String filePath = scanner.next();
        CSVToStudentExtractor csvToStudentExtractor = new CSVToStudentExtractor();
        List<Student> students = new ArrayList<>();
        CSV csv = csvParser.parseFile(filePath);
        students = csvToStudentExtractor.extract(csv);
        return students;
    }

    private StatisticCalculator selectStatisticCalculator() throws CLIException {
        int selectedOptionIndex = selectOption(
                "Please choose one of this methods:",
                statisticCalculators.stream().map(StatisticCalculator::getName).toList()
        );
        return statisticCalculators.get(selectedOptionIndex);
    }

    private ScoreCollector selectScoreCollector() throws CLIException {
        int selectedCollectorIndex = selectOption(
                "Please choose one of this targets:",
                scoreCollectors.stream().map(ScoreCollector::getTarget).toList()
        );
        return scoreCollectors.get(selectedCollectorIndex);
    }

    private boolean isContinue() {
        System.out.print("\nAre you want to continue (Y/n): ");
        String res = scanner.next();
        return !res.equals("n");
    }
}
