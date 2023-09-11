package com.basereh.app;

import com.basereh.app.Domain.StatisticsMeasureResult;
import com.basereh.app.Domain.StatisticsResult;
import com.basereh.app.Domain.Student;
import com.basereh.app.ScoreCollect.ScoreCollector;
import com.basereh.app.StatisticCalculate.StatisticCalculator;

import java.util.List;

public class StatisticsFacade {
    public List<StatisticsResult> calculateSchoolStatistics(
            List<Student> students,
            List<StatisticCalculator> statisticCalculators,
            List<ScoreCollector> scoreCollectors
    ) {
        return scoreCollectors.stream().flatMap(scoreCollector ->
                scoreCollector.collect(students).keySet().stream().map(name ->
                        new StatisticsResult(
                                name,
                                scoreCollector.getTarget(),
                                statisticCalculators.stream().map(statisticCalculator ->
                                        new StatisticsMeasureResult(
                                                statisticCalculator.getName(),
                                                statisticCalculator.apply(scoreCollector.collect(students).get(name))
                                        )
                                ).toList()
                        )
                )
        ).toList();


//        List<StatisticsResult> results = new ArrayList<>();
//
//        scoreCollectors.forEach(scoreCollector -> {
//
//            scoreCollector.collect(students).forEach((name, scores) -> {
//
//                List<StatisticsMeasureResult> measures = statisticCalculators.stream().map(statisticCalculator ->
//                        new StatisticsMeasureResult(statisticCalculator.getName(), statisticCalculator.apply(scores))
//                ).toList();
//                results.add(new StatisticsResult(name, scoreCollector.getTarget(), measures));
//
//            });
//
//        });
//        return results;
    }
}
