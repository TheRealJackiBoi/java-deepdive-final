package org.dat3.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CountryFetcher implements Fetcher {

    public Elements fetch(String url) {

        try {

        Document doc = Jsoup.connect(url).get();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
