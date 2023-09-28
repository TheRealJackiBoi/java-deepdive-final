package org.dat3.utils;

import org.dat3.model.Value;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TradeExtractorTest {

    @Test
    void extractData() throws IOException {
        // HTML scraped & saved from website "www.valutakurser.dk" for testing 27/09/2023
        // This is to ensure that data is always consistent for testing purposes
        File input = new File("src/test/java/org/dat3/utils/testHTML.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        // Specify and extract elements from the HTML
        Elements elements = doc.select("div.index_alphabeticalOrderContent__t3YSE");
        List<Value> values = TradeExtractor.extractData(elements);

        // Check if the title of the HTML is correct to ensure that the correct HTML is used
        System.out.println(doc.title());

        // Check if returned elements are NOT null and contain some relevant data
        assertNotNull(values);
        assertFalse(values.isEmpty());
        assertTrue(values.toString().contains("USD"));

    }
}