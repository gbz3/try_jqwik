package com.github.gbz3.try_jqwik;

import net.jqwik.api.*;
import net.jqwik.api.statistics.Statistics;
import org.assertj.core.api.Assertions;

import java.util.*;

public class CsvTest implements AutoCloseable {

    CsvEncoder _enc = new CsvEncoder() {};
    CsvDecoder _dec = new CsvDecoder() {};

    @Property(tries = 1000)
    void symmetric(@ForAll("csvData") List<List<String>> aCsv) {
        var rangeScale = 10;
        var rangeStart = aCsv.size() / rangeScale * rangeScale;
        var empty = aCsv.isEmpty() ? "empty": "[" + aCsv.get(0).size() + "; " + rangeStart + "-" + (rangeStart + rangeScale - 1) + "]";
        Statistics.collect(empty);

        Assertions.assertThat(_dec.decode(_enc.encode(aCsv))).isEqualTo(aCsv);
    }

    @SuppressWarnings("unused")
    @Provide
    Arbitrary<List<List<String>>> csvData() {
        var sizeNullable = Arbitraries.integers().between(1, 10).sample();
        var size = Optional.ofNullable(sizeNullable).orElse(1);

        var unquotedText = Arbitraries.strings()
                .alpha()
                .numeric()
                .ofMinLength(1);

        var quotableText = Arbitraries.strings()
                .alpha()
                .numeric()
                .withChars('\r', '\n', '"', ',')
                .ofMinLength(1);

        var field = Arbitraries.oneOf(unquotedText, quotableText);

        return field
                .list().ofSize(size)
                .list();

    }

    @Override
    public void close() {
        // Nothing
    }

}
