package com.github.gbz3.try_jqwik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public interface CsvDecoder {

    default List<List<String>> decode(String csv) {
        if (csv == null || csv.isEmpty()) return List.of();

        var tokens = new ArrayList<String>();
        AtomicReference<StringBuilder> buff = new AtomicReference<>(new StringBuilder());
        AtomicBoolean quoted = new AtomicBoolean(false);
//        AtomicInteger numOfQuote = new AtomicInteger();
//        csv.chars()
//                .mapToObj(ch -> (char)ch)
//                .forEach(ch -> {
//                    buff.get().append(ch);
//                    if (ch == '"') numOfQuote.set(numOfQuote.get() + 1);
//                    if (ch == '"' && numOfQuote.get() % 2 == 0) {
//                        tokens.add(buff.toString());
//                        buff.set(new StringBuilder());
//                        numOfQuote.set(0);
//                    }
//                });
        csv.chars()
                .mapToObj(ch -> (char)ch)
                .forEach(ch -> {
                    if (ch == '"' && buff.get().isEmpty()) {
                        quoted.set(true);
                    } else if (ch == ',' && !quoted.get()) {
                        tokens.add(buff.toString());
                        buff.get().setLength(0);
                    } else {
                        buff.get().append(ch);
                    }
                });
        if (!buff.get().isEmpty()) tokens.add(buff.toString());

        tokens.forEach(e -> System.out.println("[" + e + "]"));

        return Arrays.stream(csv.split("\r|\n|\r\n"))
                .map(line -> List.of(line.split(",")))
                .toList();
    }

}
