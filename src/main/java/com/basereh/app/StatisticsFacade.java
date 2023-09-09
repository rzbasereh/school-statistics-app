package com.basereh.app;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticsFacade {

    private <T> List<Class<? extends T>> getInterfaceSubClasses(Class<T> interfaceClass) {
        Reflections reflections = new Reflections("com.basereh.app");
        return reflections.getSubTypesOf(interfaceClass).stream().toList();
    }

    public List<Class<? extends StatisticCalculator>> getStatisticCalculators() {
        return getInterfaceSubClasses(StatisticCalculator.class);
    }

    public List<Class<? extends ScoreCollector>> getScoreCollectors() {
        return getInterfaceSubClasses(ScoreCollector.class);
    }

    private <T> T getInstanceFromClass(Class<? extends T> tClass) {
        T instance = null;
        try {
            Constructor<? extends T> constructor = tClass.getConstructor();
            instance = constructor.newInstance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return instance;
    }

    public List<StatisticsResult> calculateSchoolStatistics(
            List<Student> students,
            List<Class<? extends StatisticCalculator>> calculators,
            List<Class<? extends ScoreCollector>> targetCollectors
    ) {
        List<StatisticsResult> results = new ArrayList<>();

        targetCollectors.forEach(targetCollector -> {
            ScoreCollector collector = getInstanceFromClass(targetCollector);
            collector.collect(students).forEach((name, scores) -> {
                List<StatisticsMeasureResult> measures = new ArrayList<>();
                calculators.forEach(calculator -> {
                    measures.add(new StatisticsMeasureResult(
                            calculator.getSimpleName(),
                            getInstanceFromClass(calculator).apply(scores))
                    );
                });
                results.add(new StatisticsResult(name, collector.getTarget(), measures));
            });
        });
        return results;
    }

    public List<StatisticsResult> calculateSchoolStatistics(List<Student> students) {
        return new ArrayList<>(calculateSchoolStatistics(
                students,
                getStatisticCalculators(),
                getScoreCollectors()
        ));
    }

    public List<StatisticsResult> calculateSchoolStatistics(
            List<Student> students,
            Class<? extends StatisticCalculator> measurementMethod
    ) {
        return new ArrayList<>(calculateSchoolStatistics(
                students,
                Collections.singletonList(measurementMethod),
                getScoreCollectors()
        ));
    }
}
