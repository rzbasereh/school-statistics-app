package com.basereh.app;

import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class StudentList extends CSVExtractor {
    private final List<Student> students = new ArrayList<>();

    @Override
    public void extractFromCSV(CSV csv) throws IOException {
        Map<String, Integer> headerIndexMap = getHeaderIndexMap(csv);
        for (List<String> row : csv.getRows()) {
            String name = row.get(headerIndexMap.get("name"));
            String school = row.get(headerIndexMap.get("school"));
            String grade = row.get(headerIndexMap.get("grade"));
            String className = row.get(headerIndexMap.get("className"));
            String score = row.get(headerIndexMap.get("score"));
            if (name == null || school == null || grade == null || className == null || score == null) {
                throw new IOException("Invalid file format!");
            }
            Student student = new Student(name, school, grade, className, Float.parseFloat(score));
            students.add(student);
        }
    }
}
