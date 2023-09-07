package com.basereh.app;

public class App {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser();
        StatisticsFacade statisticsFacade = new StatisticsFacade();
        Printer printer = new Printer();
        CLI cli = new CLI(csvParser, statisticsFacade, printer);
        cli.run();
    }
}
