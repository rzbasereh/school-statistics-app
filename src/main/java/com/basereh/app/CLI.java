package com.basereh.app;

import com.basereh.app.CSVExtract.CSVToStudentExtractor;
import com.basereh.app.Domain.CSV;
import com.basereh.app.Domain.StatisticsResult;
import com.basereh.app.Domain.Student;
import com.basereh.app.Print.CSVPrinter;
import com.basereh.app.Print.StatisticResultPrinter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class CLI {
    private final Scanner scanner;
    private final CSVParser csvParser;
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
                        List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(students);
                        statisticResultPrinter.print(results);
                    }
                    case 3 -> {
                        List<Student> students = getStudentsFromCSVFile();
                        String selectedCalculatorName = selectStatisticCalculator();
                        List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(
                                students,
                                Collections.singletonList(selectedCalculatorName)
                        );
                        statisticResultPrinter.print(results);
                    }
                    case 4 -> {
                        List<Student> students = getStudentsFromCSVFile();
                        String selectedCalculatorName = selectStatisticCalculator();
                        String selectedCollectorTarget = selectScoreCollector();
                        List<StatisticsResult> results = statisticsFacade.calculateSchoolStatistics(
                                students,
                                Collections.singletonList(selectedCalculatorName),
                                selectedCollectorTarget
                        );
                        statisticResultPrinter.print(results);
                    }
                }
            } catch (SchoolStatisticsException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Some error occurred!");
            }
        } while (isContinue());
    }

    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private int selectOption(String title, List<String> options) throws SchoolStatisticsException {
        System.out.println("\n" + title);
        for (int i = 0; i < options.size(); i++) {
            System.out.println("\t[" + (i + 1) + "] " + options.get(i));
        }

        int selectedOptionIndex = scanner.nextInt() - 1;
        if (selectedOptionIndex < 0 || selectedOptionIndex >= options.size()) {
            throw new SchoolStatisticsException("Invalid option selected!");
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

    private List<Student> getStudentsFromCSVFile() throws SchoolStatisticsException {
        System.out.print("Enter your file path: ");
        String filePath = scanner.next();
        CSVToStudentExtractor csvToStudentExtractor = new CSVToStudentExtractor();
        CSV csv = csvParser.parseFile(filePath);
        return csvToStudentExtractor.extract(csv);
    }

    private String selectStatisticCalculator() throws SchoolStatisticsException {
        List<String> availableCalculators = statisticsFacade.getAvailableCalculators();
        int selectedOptionIndex = selectOption(
                "Please choose one of this methods:",
                availableCalculators
        );
        return availableCalculators.get(selectedOptionIndex);
    }

    private String selectScoreCollector() throws SchoolStatisticsException {
        List<String> availableCollectors = statisticsFacade.getAvailableCollectors();
        int selectedCollectorIndex = selectOption(
          "Please choose one of this targets:",
                availableCollectors
        );
        return availableCollectors.get(selectedCollectorIndex);
    }

    private boolean isContinue() {
        System.out.print("\nAre you want to continue (Y/n): ");
        String res = scanner.next();
        return !res.equals("n");
    }
}
