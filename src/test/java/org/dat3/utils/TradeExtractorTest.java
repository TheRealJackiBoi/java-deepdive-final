package org.dat3.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TradeExtractorTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void extractData() {
        String url = "https://www.valutakurser.dk/";
        TradeFetcher tf = new TradeFetcher();
        System.out.println(TradeExtractor.extractData(tf.fetch(url)));
    }
}