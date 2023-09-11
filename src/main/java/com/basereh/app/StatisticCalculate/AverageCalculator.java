package com.basereh.app.StatisticCalculate;

import java.util.List;

public class AverageCalculator implements StatisticCalculator {
    @Override
    public Float apply(List<Float> nums) {
        float sum = 0;
        for (Float num : nums) {
            sum += num;
        }
        return sum / nums.size();
    }

    @Override
    public String getName() {
        return "Average";
    }
}