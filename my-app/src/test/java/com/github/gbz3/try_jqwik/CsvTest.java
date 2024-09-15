package com.github.gbz3.try_jqwik;

import net.jqwik.api.*;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;

public class CsvTest implements AutoCloseable {

    CsvEncoder _enc = new CsvEncoder() {};
    CsvDecoder _dec = new CsvDecoder() {};

    @Property
    void symmetric(@ForAll("csvData") List<Map<String, String>> aCsv) {
        final var encoded = _enc.encode(aCsv);
        Assertions.assertThat(aCsv).isEqualTo(_dec.decode(encoded));
    }

    @Provide
    Arbitrary<List<Map<String, String>>> csvData(@ForAll("csvField") String aField) {
        var size = Arbitraries.integers().between(1, 10).sample();
        var keys = Arbitraries.of(aField).list().ofSize(size).sample();
        return Arbitraries
                .maps(Arbitraries.of(keys), Arbitraries.of(aField))
                .collect(list -> true);
    }

    @Provide
    Arbitrary<String> csvField() {
        var unquotedText = Arbitraries.strings()
                .alpha()
                .numeric()
                .ofMinLength(1);

        var quotableText = Arbitraries.strings()
                .alpha()
                .numeric()
                .withChars('\r', '\n', '"', ',')
                .ofMinLength(1);

        return Arbitraries.oneOf(unquotedText, quotableText);
    }

    @Override
    public void close() {
        // Nothing
    }

}
