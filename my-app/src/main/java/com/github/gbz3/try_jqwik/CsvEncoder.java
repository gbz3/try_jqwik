package com.github.gbz3.try_jqwik;

import java.util.List;
import java.util.stream.Collectors;

public interface CsvEncoder {

    default String encode(List<List<String>> csv) {
        if (csv == null || csv.isEmpty()) return "";
        if (csv.size() == 1 && csv.get(0).isEmpty()) return "";

        return csv.stream()
                .map(line -> String.join(",", line))
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
