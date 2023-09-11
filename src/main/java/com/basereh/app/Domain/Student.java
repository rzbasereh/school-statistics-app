package com.basereh.app.Domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Student {
    private final String name;

    private final String school;

    private final String grade;

    private final String className;

    private final Float score;
}
