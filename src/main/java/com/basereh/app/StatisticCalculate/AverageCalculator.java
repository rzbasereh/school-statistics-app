package com.basereh.app.StatisticCalculate;

import java.util.List;
import java.util.OptionalDouble;

public class AverageCalculator implements StatisticCalculator {
    @Override
    public Float apply(List<Float> nums) {
        OptionalDouble averageValue = nums.stream().mapToDouble(Float::doubleValue).average();
        if (averageValue.isPresent()) {
            return (float) averageValue.getAsDouble();
        } else {
            return (float) 0;
        }
    }

    @Override
    public String getName() {
        return "Average";
    }
}