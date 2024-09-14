package com.github.gbz3.try_jqwik;

import net.jqwik.api.*;
import org.assertj.core.api.*;

class PropertyBasedTest implements AutoCloseable {

    @Report({Reporting.GENERATED, Reporting.FALSIFIED})
    @Property
    boolean alwaysPositive(@ForAll int anInteger) {
        return Math.abs(anInteger) >= 0;
    }

    @Report({Reporting.GENERATED, Reporting.FALSIFIED})
    @Property
    void checkLength(@ForAll String str1, @ForAll String str2) {
        final var str = str1 + str2;
        Assertions.assertThat(str.length()).isGreaterThan(str1.length());
        Assertions.assertThat(str.length()).isGreaterThan(str2.length());
    }

    PropertyBasedTest() {
        System.out.println("### Before each ###");
    }

    @Override
    public void close() {
        System.out.println("### After each ###");
    }
}
