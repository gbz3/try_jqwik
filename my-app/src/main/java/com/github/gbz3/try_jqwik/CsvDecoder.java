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
                        token.get().append(ch);
                        if (ch == '"') {
                            tokenizedCsv.get(tokenizedCsv.size() - 1).add(token.toString());
                            token.get().setLength(0);
                        }
                    } else {
                        if (ch == ',' || ch == '\n') {
                            if (lastCh.get() != '"') {
                                tokenizedCsv.get(tokenizedCsv.size() - 1).add(token.toString());
                                token.get().setLength(0);
                            }
                            if (ch == '\n') tokenizedCsv.add(new ArrayList<>());
                            return;
                        }
                        token.get().append(ch);
                    }
                    lastCh.set(ch);
                });
        if (!token.get().isEmpty()) tokenizedCsv.get(tokenizedCsv.size() - 1).add(token.toString());

        return tokenizedCsv.stream()
                .map(line ->
                        // token 両端の " を除去  (ex. "abc" => abc )
                        line.stream()
                                .map(t -> t.startsWith("\"") && t.endsWith("\"") ? t.substring(1, t.length() - 1) : t)
                                .toList()
                )
                .map(line -> {
                    // token 境界が " であれば1個に連結  (ex. [ abc", "def ] => [ abc""def ] )
                    if (line.size() >= 2) {
                        var newLine = new ArrayList<String>();
                        newLine.add(line.get(0));
                        line.stream().skip(1).forEach(tk -> {
                            if (tk.startsWith("\"") && newLine.get(newLine.size() - 1).endsWith("\"")) {
                                newLine.set(newLine.size() - 1, newLine.get(newLine.size() - 1) + tk);
                            } else
                                newLine.add(tk);
                        });
                        return newLine;
                    } else
                        return line;
                })
                .toList();
    }

}
