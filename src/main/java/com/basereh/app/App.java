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
             List<ScoreCollector> scoreCollectors = ReflectionUtil
                    .getInstancesOf(ReflectionUtil.getSubTypesOf(ScoreCollector.class));
            List<StatisticCalculator> statisticCalculators = ReflectionUtil
                    .getInstancesOf(ReflectionUtil.getSubTypesOf(StatisticCalculator.class));

            StatisticsFacade statisticsFacade = new StatisticsFacade(statisticCalculators, scoreCollectors);
            CSVParser csvParser = new CSVParser();
            CSVPrinter csvPrinter = new CSVPrinter();
            StatisticResultPrinter statisticResultPrinter = new StatisticResultPrinter();

            CLI cli = new CLI(
                    scanner,
                    csvParser,
                    statisticsFacade,
                    csvPrinter,
                    statisticResultPrinter
            );
            cli.mainLoop();
        }
    }
}
