package com.basereh.app;

import java.util.List;

public class Average implements StatisticMeasure {

    @Override
    public float calculate(List<Integer> nums) {
        int sum = 0;
        for (int num: nums) {
            sum += num;
        }
        return (float)sum / nums.size();
    }
}