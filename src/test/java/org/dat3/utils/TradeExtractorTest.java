package org.dat3.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class TradeExtractorTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void extractData() throws IOException {
        // HTML saved from website "www.valutakurser.dk" for testing purposes 27/09/2023
        File input = new File("src/test/java/org/dat3/utils/testHTML.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        // Check if the title of the HTML is correct to ensure that the correct HTML is used
        System.out.println(doc.title());

        // Fetch elements from the HTML
        Elements elements = doc.select("div.index_alphabeticalOrderContent__t3YSE");

        // Check if returned elements are NOT null and contain some data
        assertNotNull(elements);
        assertFalse(elements.isEmpty());
    }
}