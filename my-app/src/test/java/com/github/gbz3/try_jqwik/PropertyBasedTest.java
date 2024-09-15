package com.github.gbz3.try_jqwik;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.NotEmpty;
import org.assertj.core.api.*;

class PropertyBasedTest implements AutoCloseable {

    @Property
    boolean alwaysPositive(@ForAll @IntRange(min = Integer.MIN_VALUE + 1) int anInteger) {
        return Math.abs(anInteger) >= 0;
    }

    @Property
    void checkLength(@ForAll @NotEmpty String str1, @ForAll @NotEmpty String str2) {
        final var str = str1 + str2;
        Assertions.assertThat(str.length()).isGreaterThan(str1.length());
        Assertions.assertThat(str.length()).isGreaterThan(str2.length());
    }

    static final String NOT_MINIMUM_INT = "exclude MIN_VALUE from Integer";
    @Property
    boolean alwaysPositive2(@ForAll(NOT_MINIMUM_INT) int anInteger) {
        return Math.abs(anInteger) >= 0;
    }

    @Provide(NOT_MINIMUM_INT)
    Arbitrary<Integer> genIntVal() {
        return Arbitraries.integers().between(Integer.MIN_VALUE + 1, Integer.MAX_VALUE);
    }

    @Property
    boolean alwaysPositive3(@ForAll(supplier = ExcludeMinValueInteger.class) int anInteger) {
        return Math.abs(anInteger) >= 0;
    }

    PropertyBasedTest() {
        System.out.println("### Before each ###");
    }

    @Override
    public void close() {
        System.out.println("### After each ###");
    }
}

class ExcludeMinValueInteger implements ArbitrarySupplier<Integer> {
    @Override
    public Arbitrary<Integer> get() {
        return Arbitraries.integers().between(Integer.MIN_VALUE + 1, Integer.MAX_VALUE);
    }
}
