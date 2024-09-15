package com.github.gbz3.try_jqwik;

import net.jqwik.api.*;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;

public class CsvTest implements AutoCloseable {

    CsvEncoder _enc = new CsvEncoder() {};;
    CsvDecoder _dec = new CsvDecoder() {};;

    @Property
    void symmetric(@ForAll List<Map<String, String>> aCsv) {
        final var encoded = _enc.encode(aCsv);
        Assertions.assertThat(aCsv).isEqualTo(_dec.decode(encoded));
    }

    @Override
    public void close() throws Exception {
        // Nothing
    }

}
