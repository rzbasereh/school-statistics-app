package com.basereh.app;

import com.basereh.app.Domain.StatisticsMeasureResult;
import com.basereh.app.Domain.StatisticsResult;
import com.basereh.app.Domain.Student;
import com.basereh.app.ScoreCollect.ScoreCollector;
import com.basereh.app.StatisticCalculate.StatisticCalculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StatisticsFacade {
    public Map<String ,StatisticCalculator> nameToStatisticCalculators;
    public Map<String ,ScoreCollector> targetToScoreCollectors;

    public StatisticsFacade(List<StatisticCalculator> statisticCalculators, List<ScoreCollector> scoreCollectors) {
        nameToStatisticCalculators = statisticCalculators.stream().collect(Collectors.toMap(StatisticCalculator::getName, Function.identity()));
        targetToScoreCollectors = scoreCollectors.stream().collect(Collectors.toMap(ScoreCollector::getTarget, Function.identity()));
    }

    public List<String> getAvailableCalculators() {
        return new ArrayList<>(nameToStatisticCalculators.keySet());
    }

    public List<String> getAvailableCollectors() {
        return new ArrayList<>(targetToScoreCollectors.keySet());
    }

    public List<StatisticsResult> calculateSchoolStatistics(
            List<Student> students,
            List<String> statisticCalculatorNames,
            String collectorTarget
    ) {
        ScoreCollector scoreCollector = targetToScoreCollectors.get(collectorTarget);
        List<StatisticCalculator> statisticCalculators = statisticCalculatorNames.stream().map(nameToStatisticCalculators::get).toList();

        return scoreCollector.collect(students).entrySet().stream().map(collectorEntry ->
                        new StatisticsResult(
                                collectorEntry.getKey(),
                                scoreCollector.getTarget(),
                                statisticCalculators.stream().map(statisticCalculator ->
                                        new StatisticsMeasureResult(
                                                statisticCalculator.getName(),
                                                statisticCalculator.apply(collectorEntry.getValue())
                                        )
                                ).toList()
                        )
                ).toList();
    }

    public List<StatisticsResult> calculateSchoolStatistics(
            List<Student> students,
            List<String> statisticCalculatorNames
    ) {
        return targetToScoreCollectors.keySet().stream()
                .map(target -> calculateSchoolStatistics(students, statisticCalculatorNames, target))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<StatisticsResult> calculateSchoolStatistics(List<Student> students) {
        return calculateSchoolStatistics(students, new ArrayList<>(nameToStatisticCalculators.keySet()));
    }
}
