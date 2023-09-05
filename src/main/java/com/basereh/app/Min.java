package com.basereh.app;

import java.util.List;

public class Min implements StatisticMeasure {

    @Override
    public float calculate(List<Integer> nums) {
        float min = 0;
        for (int num: nums) {
            if (num < min) min = num;
        }
        return min;
    }
}