package com.basereh.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class CSVExtractor {
    public Map<String, Integer> getHeaderIndexMap(CSV csv) {
        HashMap<String, Integer> headerIndexMap = new HashMap<>();
        for (int i = 0; i < csv.getHeaders().size(); i++) {
            headerIndexMap.put(csv.getHeaders().get(i), i);
        }
        return headerIndexMap;
    }

    public abstract void extractFromCSV(CSV csv)  throws IOException;
}
