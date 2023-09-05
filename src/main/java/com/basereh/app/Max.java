package com.basereh.app;

import java.util.List;

public class Max implements StatisticMeasure {

    @Override
    public float calculate(List<Integer> nums) {
        float max = 0;
        for (int num: nums) {
            if (num > max) max = num;
        }
        return max;
    }
}