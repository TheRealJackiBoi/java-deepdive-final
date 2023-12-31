package org.dat3.utils;

import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TradeFetcherTest {

    @Test
    void fetch() {
        // URL for scraping
        String url = "https://www.valutakurser.dk/";

        // Fetch elements from the URL
        Elements elements = TradeFetcher.fetch(url);

        // Check if returned elements are NOT null and contain some data
        assertNotNull(elements);
        assertFalse(elements.isEmpty());
        assertTrue(elements.toString().contains("USD"));
        assertTrue(elements.hasClass("currencyItem_currencyNameContainer__19YHn"));
    }
}