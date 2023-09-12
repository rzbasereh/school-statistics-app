package com.basereh.app.Domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class StatisticsResult {
    private final String name;
    private final String target;
    private final List<StatisticsMeasureResult> measures;
}
