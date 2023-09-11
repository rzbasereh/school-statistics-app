package com.basereh.app.StatisticCalculate;

import java.util.List;

public class MaxCalculator implements StatisticCalculator {
    @Override
    public float apply(List<Float> nums) {
        float max = nums.get(0);
        for (float num: nums) {
            if (num > max) max = num;
        }
        return max;
    }

    @Override
    public String getName() {
        return "Max";
    }
}