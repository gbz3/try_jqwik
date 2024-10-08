package com.github.gbz3.try_jqwik;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public interface CsvDecoder {

    default List<List<String>> decode(String csv) {
        if (csv == null || csv.isEmpty()) return List.of();

        // token 分割
        var tokenizedCsv = new ArrayList<List<String>>();
        tokenizedCsv.add(new ArrayList<>());
        AtomicReference<StringBuilder> token = new AtomicReference<>(new StringBuilder());
        AtomicReference<Character> lastCh = new AtomicReference<>((char) 0);
        csv.chars()
                .mapToObj(ch -> (char)ch)
                .forEach(ch -> {
                    if (!token.get().isEmpty() && token.get().charAt(0) == '"') {
                        // 二重引用符で括られたフィールドの終端判定（フィールド内の二重引用符の個数は偶数）
                        if ((ch == ',' || ch == '\n') && lastCh.get() == '"' && token.get().chars().filter(c -> c == '"').count() % 2 == 0) {
                            tokenizedCsv.get(tokenizedCsv.size() - 1).add(token.toString());
                            token.get().setLength(0);
                            if (ch == '\n') tokenizedCsv.add(new ArrayList<>());
                        } else {
                            token.get().append(ch);
                        }
                    } else {
                        // 通常フィールドの終端判定
                        if (ch == ',' || ch == '\n') {
                            tokenizedCsv.get(tokenizedCsv.size() - 1).add(token.toString());
                            token.get().setLength(0);
                            if (ch == '\n') tokenizedCsv.add(new ArrayList<>());
                        } else {
                            token.get().append(ch);
                        }
                    }
                    lastCh.set(ch);
                });
        if (!token.get().isEmpty()) tokenizedCsv.get(tokenizedCsv.size() - 1).add(token.toString());

        return tokenizedCsv.stream()
                .map(line ->
                        // token 両端の " を除去  (ex. "abc" => abc )
                        line.stream()
                                .map(t -> t.startsWith("\"") && t.endsWith("\"") ? t.substring(1, t.length() - 1) : t)
                                .map(t -> t.replaceAll("\"\"", "\""))
                                .toList()
                )
                .toList();
    }

}
