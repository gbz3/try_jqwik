package com.github.gbz3.try_jqwik;

import java.util.Arrays;
import java.util.List;

public interface CsvDecoder {

    default List<List<String>> decode(String csv) {
        if (csv == null || csv.isEmpty()) return List.of();

        return Arrays.stream(csv.split("\r|\n|\r\n"))
                .map(line -> List.of(line.split(",")))
                .toList();
    }

}
