package com.basereh.app.StatisticCalculate;

import java.util.List;

public class MinCalculator implements StatisticCalculator {
    @Override
    public Float apply(List<Float> nums) {
        Float min = nums.get(0);
        for (Float num: nums) {
            if (num < min) min = num;
        }
        return min;
    }

    @Override
    public String getName() {
        return "Min";
    }
}