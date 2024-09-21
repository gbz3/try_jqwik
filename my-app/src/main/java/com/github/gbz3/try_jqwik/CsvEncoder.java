package com.github.gbz3.try_jqwik;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface CsvEncoder {

    default String encode(List<List<String>> csv) {
        if (csv == null || csv.isEmpty()) return "";
        if (csv.size() == 1 && csv.get(0).isEmpty()) return "";

        Predicate<String> quotable = s -> s.indexOf(',') >= 0 || s.indexOf('\n') >= 0 || s.indexOf('"') >= 0;
        return csv.stream()
                .map(line -> line.stream()
                        .map(field -> quotable.test(field)? "\"" + field.replaceAll("\"", "\"\"") + "\"": field)
                        .collect(Collectors.joining(",")))
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
