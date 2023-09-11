package com.basereh.app;

import com.basereh.app.Print.CSVPrinter;
import com.basereh.app.Print.StatisticResultPrinter;
import com.basereh.app.ScoreCollect.ScoreCollector;
import com.basereh.app.StatisticCalculate.StatisticCalculator;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in).useDelimiter("\n")) {
            CSVParser csvParser = new CSVParser();
            StatisticsFacade statisticsFacade = new StatisticsFacade();
            CSVPrinter csvPrinter = new CSVPrinter();
            StatisticResultPrinter statisticResultPrinter = new StatisticResultPrinter();
            ReflectionUtil reflectionUtil = new ReflectionUtil();
            List<ScoreCollector> scoreCollectors = reflectionUtil
                    .getInstancesOf(reflectionUtil.getSubTypesOf(ScoreCollector.class));
            List<StatisticCalculator> statisticCalculators = reflectionUtil
                    .getInstancesOf(reflectionUtil.getSubTypesOf(StatisticCalculator.class));

            CLI cli = new CLI(
                    scanner,
                    csvParser,
                    scoreCollectors,
                    statisticCalculators,
                    statisticsFacade,
                    csvPrinter,
                    statisticResultPrinter
            );
            cli.mainLoop();
        }
    }
}
