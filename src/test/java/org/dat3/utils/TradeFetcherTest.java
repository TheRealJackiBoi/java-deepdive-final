package org.dat3.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TradeFetcherTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void fetch() {
        String url = "https://finance.yahoo.com/currencies";
        TradeFetcher tf = new TradeFetcher();
        System.out.println(tf.fetch(url));
    }
}