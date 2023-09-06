package com.basereh.app;

import java.util.List;

public class Average implements StatisticMeasure {

    @Override
    public float calculate(List<Float> nums) {
        float sum = 0;
        for (float num: nums) {
            sum += num;
        }
        return sum / nums.size();
    }
}