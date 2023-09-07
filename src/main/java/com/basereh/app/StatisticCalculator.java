package com.basereh.app;

import java.util.List;

public interface StatisticCalculator {
    float apply(List<Float> nums);

    String getName();
}