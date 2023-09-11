package com.basereh.app.CSVExtract;

import com.basereh.app.CLIException;
import com.basereh.app.Domain.CSV;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class CSVExtractor<T> {
    protected Map<String, Integer> getHeaderIndexMap(CSV csv) {
        HashMap<String, Integer> headerIndexMap = new HashMap<>();
        for (int i = 0; i < csv.getHeaders().size(); i++) {
            headerIndexMap.put(csv.getHeaders().get(i), i);
        }
        return headerIndexMap;
    }

    public abstract Collection<T> extract(CSV csv) throws IOException, CLIException;
}
