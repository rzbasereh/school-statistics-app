package com.basereh.app;

import java.util.List;

public class Min implements StatisticMeasure {

    @Override
    public float calculate(List<Float> nums) {
        float min = 0;
        for (float num: nums) {
            if (num < min) min = num;
        }
        return min;
    }
}