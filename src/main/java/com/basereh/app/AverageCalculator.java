package com.basereh.app;

import java.util.List;

public class AverageCalculator implements StatisticCalculator {
    @Override
    public float apply(List<Float> nums) {
        float sum = 0;
        for (float num : nums) {
            sum += num;
        }
        return sum / nums.size();
    }

    @Override
    public String getName() {
        return "Average";
    }
}