package org.dat3.utils;

import org.jsoup.select.Elements;

public interface Fetcher {

    public Elements fetch(String url);

}
