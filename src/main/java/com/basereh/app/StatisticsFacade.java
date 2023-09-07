package com.basereh.app;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsFacade {

    public List<StatisticTarget> getStatisticTargets() {
        return Arrays.stream(StatisticTarget.values()).toList();
    }

    public Set<Class<? extends StatisticMeasure>> getMeasurementMethods() {
        Reflections reflections = new Reflections("com.basereh.app");
        return reflections.getSubTypesOf(StatisticMeasure.class);
    }

    public Map<String, List<Float>> groupStudentScoresBy(StudentList studentList, StatisticTarget target) {
        return studentList.getStudents().stream().collect(Collectors.groupingBy(student -> switch (target) {
            case CLASS -> student.getClassName();
            case GRADE -> student.getGrade();
            default -> student.getSchool();
        }, Collectors.mapping(Student::getScore, Collectors.toList())));
    }

    public List<StatisticsResult> calculateSchoolStatistics(StudentList studentList) {
        List<StatisticsResult> results = new ArrayList<>();
        List<String> measurementMethodNames = getMeasurementMethods().stream().map(Class::getSimpleName).toList();
        results.addAll(calculateSchoolStatistics(studentList, measurementMethodNames, StatisticTarget.SCHOOL));
        results.addAll(calculateSchoolStatistics(studentList, measurementMethodNames, StatisticTarget.GRADE));
        results.addAll(calculateSchoolStatistics(studentList, measurementMethodNames, StatisticTarget.CLASS));
        return results;
    }

    public List<StatisticsResult> calculateSchoolStatistics(StudentList studentList, String measurementMethodName) {
        List<StatisticsResult> results = new ArrayList<>();
        List<String> measurementMethodNames = Collections.singletonList(measurementMethodName);
        results.addAll(calculateSchoolStatistics(studentList, measurementMethodNames, StatisticTarget.SCHOOL));
        results.addAll(calculateSchoolStatistics(studentList, measurementMethodNames, StatisticTarget.GRADE));
        results.addAll(calculateSchoolStatistics(studentList, measurementMethodNames, StatisticTarget.CLASS));
        return results;
    }

    public List<StatisticsResult> calculateSchoolStatistics(StudentList studentList, List<String> measurementMethodNames, StatisticTarget target) {
        List<StatisticsResult> results = new ArrayList<>();
        Map<String, List<Float>> groupStudentScores = groupStudentScoresBy(studentList, target);
        groupStudentScores.forEach((name, scores) -> {
            List<StatisticsMeasureResult> measures = new ArrayList<>();
            getMeasurementMethods().forEach(mClass -> {
                if (measurementMethodNames.contains(mClass.getSimpleName())) {
                    try {
                        Constructor<? extends StatisticMeasure> mConstructor = mClass.getConstructor();
                        StatisticMeasure statisticMeasure = mConstructor.newInstance();
                        measures.add(new StatisticsMeasureResult(mClass.getSimpleName(), statisticMeasure.calculate(scores)));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
            });
            results.add(new StatisticsResult(name, target, measures));
        });
        return results;
    }
}
