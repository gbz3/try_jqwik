package com.github.gbz3.try_jqwik;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public interface CsvDecoder {

    default List<List<String>> decode(String csv) {
        if (csv == null || csv.isEmpty()) return List.of();

        var csvData = new ArrayList<List<String>>();
        csvData.add(new ArrayList<>());
        AtomicReference<StringBuilder> token = new AtomicReference<>(new StringBuilder());
        AtomicBoolean quoted = new AtomicBoolean(false);
        csv.chars()
                .mapToObj(ch -> (char)ch)
                .forEach(ch -> {
                    if (ch == '"' && token.get().isEmpty()) {
                        quoted.set(true);
                    } else if (ch == ',' && !quoted.get()) {
                        csvData.get(csvData.size() - 1).add(token.toString());
                        token.get().setLength(0);
                    } else if (ch == '\n' && !quoted.get()) {
                        csvData.get(csvData.size() - 1).add(token.toString());
                        token.get().setLength(0);
                        csvData.add(new ArrayList<>());
                    } else {
                        token.get().append(ch);
                    }
                });
        if (!token.get().isEmpty()) csvData.get(csvData.size() - 1).add(token.toString());
        //csvData.forEach(line -> System.out.println("[" + String.join(", ", line) + "]"));
        return csvData;
    }

}
