package com.basereh.app.ScoreCollect;

import com.basereh.app.StatisticTarget;
import com.basereh.app.Student;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BySchoolScoreCollector implements ScoreCollector {
    @Override
    public Map<String, List<Float>> collect(List<Student> students) {
        return students.stream().collect(Collectors.groupingBy(
                Student::getSchool,
                Collectors.mapping(Student::getScore, Collectors.toList())
        ));
    }

    @Override
    public StatisticTarget getTarget() {
        return StatisticTarget.SCHOOL;
    }
}
