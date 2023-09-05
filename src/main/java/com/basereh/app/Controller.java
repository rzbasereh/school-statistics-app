package com.basereh.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {
    public List<Student> convertCSVToStudent(CSV csv) {
        HashMap<String, Integer> fieldToIndexMap = new HashMap<>();
        for (int i = 0; i < csv.getHeaders().size(); i++) {
            fieldToIndexMap.put(csv.getHeaders().get(i), i);
        }

        List<Student> students = new ArrayList<>();
        csv.getRows().forEach(row -> {
            Student student = new Student(
                    row.get(fieldToIndexMap.get("name")),
                    row.get(fieldToIndexMap.get("school")),
                    row.get(fieldToIndexMap.get("grade")),
                    row.get(fieldToIndexMap.get("className")),
                    Float.parseFloat(row.get(fieldToIndexMap.get("score")))
            );
            students.add(student);
        });
        return students;
    }
}
