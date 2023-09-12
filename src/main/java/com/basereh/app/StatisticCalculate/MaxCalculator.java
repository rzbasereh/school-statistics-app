package com.basereh.app.StatisticCalculate;

import java.util.List;

public class MaxCalculator implements StatisticCalculator {
    @Override
    public Float apply(List<Float> nums) {
        Float max = nums.get(0);
        for (Float num: nums) {
            if (num > max) max = num;
        }
        return max;
    }

    @Override
    public String getName() {
        return "Max";
    }
}