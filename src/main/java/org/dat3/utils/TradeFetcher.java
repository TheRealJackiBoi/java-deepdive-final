package org.dat3.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TradeFetcher implements Fetcher {
    @Override
    public Elements fetch(String url) {
        // URL to scrape data from
        url = "https://www.valutakurser.dk/";

        try {
            Document doc = Jsoup.connect(url).get();

            // Returns all currency related data in the div to scrape
            return doc.select("div.currenciesList_popularCurrenciesContent__9WNtx");

        } catch (IOException e) {
            throw new RuntimeException("An error occurred while attempting to scrape the URL.");
        }
    }
}
