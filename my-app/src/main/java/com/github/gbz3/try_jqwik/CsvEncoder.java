package com.github.gbz3.try_jqwik;

import java.util.List;
import java.util.Map;

public interface CsvEncoder {

    default String encode(List<Map<String, String>> csv) {
        return "";
    }

}
