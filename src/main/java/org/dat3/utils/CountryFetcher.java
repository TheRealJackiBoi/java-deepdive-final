package org.dat3.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CountryFetcher implements Fetcher {


    public static String fetch(String url, String currency) {
        String result = null;
        try {

            String completeUrl = url + currency;
            System.out.println(completeUrl);

            result = HTTPUtils.fetchData(completeUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
