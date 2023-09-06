package com.basereh.app;

public class App {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser();
        Printer printer = new Printer();
        CLI cli = new CLI(csvParser, printer);
        cli.run();
    }
}
