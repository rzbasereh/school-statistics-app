package com.basereh.app.StatisticCalculate;

import java.util.List;

public class AverageCalculator implements StatisticCalculator {
    @Override
    public Float apply(List<Float> nums) {
        return Float.parseFloat(nums.stream().mapToDouble(Float::doubleValue).average().toString());
    }

    @Override
    public String getName() {
        return "Average";
    }
}