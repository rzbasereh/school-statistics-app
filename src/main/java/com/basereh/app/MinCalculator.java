package com.basereh.app;

import java.util.List;

public class MinCalculator implements StatisticCalculator {
    @Override
    public float apply(List<Float> nums) {
        float min = nums.get(0);
        for (float num: nums) {
            if (num < min) min = num;
        }
        return min;
    }

    @Override
    public String getName() {
        return "Min";
    }
}