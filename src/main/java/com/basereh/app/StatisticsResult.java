package com.basereh.app;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class StatisticsResult {
    private final String name;
    private final Map<String, Float> items;
}
