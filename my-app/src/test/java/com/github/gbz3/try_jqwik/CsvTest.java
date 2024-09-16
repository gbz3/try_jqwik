package com.github.gbz3.try_jqwik;

import net.jqwik.api.*;
import net.jqwik.api.statistics.Statistics;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.MapEntry;

import java.util.*;
import java.util.stream.Collectors;

public class CsvTest implements AutoCloseable {

    CsvEncoder _enc = new CsvEncoder() {};
    CsvDecoder _dec = new CsvDecoder() {};

    @Property
    void symmetric(@ForAll("csvData") List<Map<String, String>> aCsv) {
        Statistics.collect(aCsv.size());

        final var encoded = _enc.encode(aCsv);
        Assertions.assertThat(aCsv).isEqualTo(_dec.decode(encoded));
    }

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
        //var colNames = field.set().ofSize(size);

        var aMap = Arbitraries.maps(field, field).ofSize(size);

        return aMap.list();
    }

    @Override
    public void close() {
        // Nothing
    }

}
