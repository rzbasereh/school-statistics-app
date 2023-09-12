package com.basereh.app.Domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
public class Student {
    private String name;

    private String school;

    private String grade;

    private String className;

    private Float score;
}
