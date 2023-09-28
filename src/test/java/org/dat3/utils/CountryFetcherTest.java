package org.dat3.utils;

import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryFetcherTest {

    @Test
    void fetch() {
        // URL for scraping
        String url = "https://restcountries.com/v3.1/currency/";

        // Fetch elements from the URL
        String json = CountryFetcher.fetch(url, "EUR");

        // Check if returned elements are NOT null, contains data and contains the country Germany
        assertNotNull(json);
        assertFalse(json.isEmpty());
        assertTrue(json.contains("Germany"));
    }
}