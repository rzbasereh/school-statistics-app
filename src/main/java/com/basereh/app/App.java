package com.basereh.app;

import com.basereh.app.Print.CSVPrinter;
import com.basereh.app.Print.Printer;
import com.basereh.app.Print.StatisticResultPrinter;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        CSVParser csvParser = new CSVParser();
        StatisticsFacade statisticsFacade = new StatisticsFacade();
        CSVPrinter csvPrinter = new CSVPrinter();
        StatisticResultPrinter statisticResultPrinter = new StatisticResultPrinter();
        CLI cli = new CLI(scanner, csvParser, statisticsFacade, csvPrinter, statisticResultPrinter);
        cli.mainLoop();
        scanner.close();
    }
}
