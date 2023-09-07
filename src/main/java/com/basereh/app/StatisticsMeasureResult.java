package com.basereh.app;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StatisticsMeasureResult {
    private final String name;
    private final float value;
}
