package com.basereh.app;

public class App {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser();
        CLI cli = new CLI(csvParser);
        cli.run();
    }
}
