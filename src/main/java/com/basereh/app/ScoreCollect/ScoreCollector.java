package com.basereh.app.ScoreCollect;

import com.basereh.app.Student;

import java.util.List;
import java.util.Map;

public interface ScoreCollector {
    Map<String, List<Float>> collect(List<Student> students);

    String getTarget();
}
