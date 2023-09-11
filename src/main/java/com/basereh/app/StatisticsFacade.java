package com.basereh.app;

import com.basereh.app.Domain.StatisticsMeasureResult;
import com.basereh.app.Domain.StatisticsResult;
import com.basereh.app.Domain.Student;
import com.basereh.app.ScoreCollect.ScoreCollector;
import com.basereh.app.StatisticCalculate.StatisticCalculator;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFacade {
    public List<StatisticsResult> calculateSchoolStatistics(
            List<Student> students,
            List<StatisticCalculator> statisticCalculators,
            List<ScoreCollector> scoreCollectors
    ) {
        List<StatisticsResult> results = new ArrayList<>();

        scoreCollectors.forEach(scoreCollector -> {
            scoreCollector.collect(students).forEach((name, scores) -> {
                List<StatisticsMeasureResult> measures = new ArrayList<>();
                statisticCalculators.forEach(statisticCalculator -> {
                    measures.add(
                            new StatisticsMeasureResult(statisticCalculator.getName(), statisticCalculator.apply(scores))
                    );
                });
                results.add(new StatisticsResult(name, scoreCollector.getTarget(), measures));
            });
        });
        return results;
    }
}
