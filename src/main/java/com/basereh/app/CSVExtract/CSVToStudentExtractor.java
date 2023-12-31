package com.basereh.app.CSVExtract;

import com.basereh.app.SchoolStatisticsException;
import com.basereh.app.Domain.CSV;
import com.basereh.app.Domain.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CSVToStudentExtractor extends CSVExtractor<Student> {
    @Override
    public List<Student> extract(CSV csv) throws SchoolStatisticsException {
        List<Student> students = new ArrayList<>();
        Map<String, Integer> headerIndexMap = getHeaderIndexMap(csv);
        for (List<String> row : csv.getRows()) {
            String name = row.get(headerIndexMap.get("name"));
            String school = row.get(headerIndexMap.get("school"));
            String grade = row.get(headerIndexMap.get("grade"));
            String className = row.get(headerIndexMap.get("className"));
            String score = row.get(headerIndexMap.get("score"));
            if (name == null || school == null || grade == null || className == null || score == null) {
                throw new SchoolStatisticsException("Invalid file format!");
            }
            students.add(
                    Student.builder()
                            .name(name)
                            .school(school)
                            .grade(grade)
                            .className(className)
                            .score(Float.parseFloat(score))
                            .build()
            );
        }
        return students;
    }
}
