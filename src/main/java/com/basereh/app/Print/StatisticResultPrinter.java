package com.basereh.app.Print;

import com.basereh.app.StatisticsResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticResultPrinter implements Printer<List<StatisticsResult>> {
    public void print(List<StatisticsResult> results) {
        Map<String, List<StatisticsResult>> groupedResults = results.stream().collect(Collectors.groupingBy(StatisticsResult::getTarget));
        groupedResults.forEach((statisticTarget, statisticsResults) -> {
            System.out.println("\n" + statisticTarget);
            statisticsResults.forEach(result -> {
                System.out.println("\t" + result.getName());
                result.getMeasures().forEach(measure -> {
                    System.out.println("\t\t" + measure.getName() + ":" + measure.getValue());
                });
            });
        });
    }
}
