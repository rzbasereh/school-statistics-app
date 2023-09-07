package com.basereh.app;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Student {
    private final String name;

    private final String school;

    private final String grade;

    private final String className;

    private final float score;
}
