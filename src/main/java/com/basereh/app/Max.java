package com.basereh.app;

import java.util.List;

public class Max implements StatisticMeasure {

    @Override
    public float calculate(List<Float> nums) {
        float max = 0;
        for (float num: nums) {
            if (num > max) max = num;
        }
        return max;
    }
}