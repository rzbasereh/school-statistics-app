package com.basereh.app;

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

    public void addRow(List<String> row) throws IOException {
        if (row.size() != headers.size()) {
            throw new IOException("Invalid CSV format");
        }
        rows.add(row);
    }
}
