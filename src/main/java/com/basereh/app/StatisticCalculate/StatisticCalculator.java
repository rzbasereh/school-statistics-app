package com.basereh.app.StatisticCalculate;

import java.util.List;

public interface StatisticCalculator {
    Float apply(List<Float> nums);

    String getName();
}