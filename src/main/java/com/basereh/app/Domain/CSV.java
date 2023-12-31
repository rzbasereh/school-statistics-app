package com.basereh.app.Domain;

import com.basereh.app.SchoolStatisticsException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CSV {
    private final List<String> headers;

    private List<List<String>> rows = new ArrayList<>();

    public void addRow(List<String> row) throws SchoolStatisticsException {
        if (row.size() != headers.size()) {
            throw new SchoolStatisticsException("Invalid CSV format");
        }
        rows.add(row);
    }
}
