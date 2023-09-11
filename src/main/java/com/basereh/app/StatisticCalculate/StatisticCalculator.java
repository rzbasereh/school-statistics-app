package com.basereh.app.StatisticCalculate;

import java.util.List;

public interface StatisticCalculator {
    float apply(List<Float> nums);

    String getName();
}