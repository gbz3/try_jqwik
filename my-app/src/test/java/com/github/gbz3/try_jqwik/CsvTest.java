package com.github.gbz3.try_jqwik;

import net.jqwik.api.*;
import net.jqwik.api.statistics.Statistics;
import org.assertj.core.api.Assertions;

import java.util.*;

public class CsvTest implements AutoCloseable {

    CsvEncoder _enc = new CsvEncoder() {};
    CsvDecoder _dec = new CsvDecoder() {};

    @Property(tries = 100)
    void symmetric(@ForAll("csvData") List<Map<String, String>> aCsv) {
        var empty = aCsv.isEmpty() ? "empty": "[" + aCsv.size() + "]";
        //var sumOfSize = aCsv.isEmpty() ? "*": aCsv.stream().map(Map::size).reduce(Integer::sum).orElse(0);
        Statistics.collect(empty);

        final var encoded = _enc.encode(aCsv);
        Assertions.assertThat(aCsv).isEqualTo(_dec.decode(encoded));
    }

    @SuppressWarnings("unused")
    @Provide
    Arbitrary<List<Map<String, String>>> csvData() {
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
//        var colNames = field.set().ofSize(size);

        return Arbitraries
                .maps(field, field)
                .ofSize(size)
                .list()
                ;

    }

    @Override
    public void close() {
        // Nothing
    }

}
