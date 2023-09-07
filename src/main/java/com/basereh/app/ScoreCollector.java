package com.basereh.app;

import java.util.List;
import java.util.Map;

public interface ScoreCollector {
    Map<String, List<Float>> collect(List<Student> students);

    StatisticTarget getTarget();
}
