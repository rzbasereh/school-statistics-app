package com.basereh.app;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        CSVParser csvParser = new CSVParser();
        StatisticsFacade statisticsFacade = new StatisticsFacade();
        Printer printer = new Printer();
        CLI cli = new CLI(scanner, csvParser, statisticsFacade, printer);
        cli.run();
        scanner.close();
    }
}
