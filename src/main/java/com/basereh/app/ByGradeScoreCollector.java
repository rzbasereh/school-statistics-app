package com.basereh.app;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ByGradeScoreCollector implements ScoreCollector {
    @Override
    public Map<String, List<Float>> collect(List<Student> students) {
        return students.stream().collect(Collectors.groupingBy(
                Student::getGrade,
                Collectors.mapping(Student::getScore, Collectors.toList())
        ));
    }

    @Override
    public StatisticTarget getTarget() {
        return StatisticTarget.GRADE;
    }
}
