package com.basereh.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsFacade {

    public Map<String, List<Float>> groupStudentScoresBy(StudentList studentList, StatisticTarget target) {
        return studentList.getStudents().stream().collect(Collectors.groupingBy(student -> switch (target) {
            case CLASS -> student.getClassName();
            case GRADE -> student.getGrade();
            default -> student.getSchool();
        }, Collectors.mapping(Student::getScore, Collectors.toList())));
    }

    public List<StatisticsResult> calculateSchoolStatistics(StudentList studentList) {
        List<StatisticsResult> results = new ArrayList<>();
        results.addAll(calculateSchoolStatistics(studentList, StatisticTarget.SCHOOL));
        results.addAll(calculateSchoolStatistics(studentList, StatisticTarget.GRADE));
        results.addAll(calculateSchoolStatistics(studentList, StatisticTarget.CLASS));
        return results;
    }

    public List<StatisticsResult> calculateSchoolStatistics(StudentList studentList, StatisticTarget target) {
        List<StatisticsResult> results = new ArrayList<>();
        Map<String, List<Float>> groupStudentScores = groupStudentScoresBy(studentList, target);
        groupStudentScores.forEach((name, scores) -> {
            List<StatisticsMeasureResult> measures = new ArrayList<>();
            measures.add(new StatisticsMeasureResult("Min", new Min().calculate(scores)));
            measures.add(new StatisticsMeasureResult("Max", new Max().calculate(scores)));

            results.add(new StatisticsResult(name, target, measures));
        });
        return results;
    }
}
