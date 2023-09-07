package com.basereh.app;

import java.util.*;
import java.util.stream.Collectors;

public class Printer {
    public void print(CSV csv) {
        int[] maxLengths = new int[csv.getHeaders().size()];
        List<List<String>> records = new ArrayList<>();
        records.add(csv.getHeaders());
        records.addAll(csv.getRows());

        for (List<String> record : records) {
            for (int i = 0; i < record.size(); i++) {
                maxLengths[i] = Math.max(maxLengths[i], record.get(i).length());
            }
        }
        StringBuilder formatBuilder = new StringBuilder();
        for (int maxLength : maxLengths) {
            formatBuilder.append("|%-").append(maxLength + 2).append("s");
        }
        String format = formatBuilder.toString();

        StringBuilder tableBuilder = new StringBuilder();
        for (List<String> record : records) {
            tableBuilder.append(String.format(format, record.toArray())).append("\n");
        }
        System.out.println(tableBuilder);
    }

    public void print(List<StatisticsResult> results) {
        Map<StatisticTarget, List<StatisticsResult>> groupedResults = results.stream().collect(Collectors.groupingBy(StatisticsResult::getTarget));
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
