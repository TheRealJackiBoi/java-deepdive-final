package org.dat3.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TradeFetcher implements Fetcher {
    @Override
    public Elements fetch(String url) {
        // URL to scrape data from
        url = "https://finance.yahoo.com/currencies";

        try {
            Document doc = Jsoup.connect(url).get();

            Elements data = doc.select("//*[@id=\"yfin-list\"]/div[2]");
            data.addAll(doc.select("//*[@id=\"yfin-list\"]/div[2]"));

            return data;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
