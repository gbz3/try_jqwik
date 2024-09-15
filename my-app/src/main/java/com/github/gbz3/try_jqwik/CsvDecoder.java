package com.github.gbz3.try_jqwik;

import java.util.List;
import java.util.Map;

public interface CsvDecoder {

    default List<Map<String, String>> decode(String csv) {
        return List.of(Map.of("", ""));
    }

}
