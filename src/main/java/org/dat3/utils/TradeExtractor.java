package org.dat3.utils;

import jakarta.persistence.EntityManagerFactory;
import org.dat3.model.Value;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TradeExtractor {

    public static List<Value> extractData(Elements elements) {
        // check om currency eksisiter, ellers opret en
        // returner en value

        //EntityManagerFactory emf = HibernateConfig.createEntityManagerFactoryConfig("DBNAME");
/*
        List<Value> values = elements.stream()
                .map(element -> {
                    Value value = new Value(
                            // Current value of currency
                            Double.parseDouble(element.select("div.currencyItem_actualValueContainer__2xLkB").text().replace(",", ".")),
                            // Time at the date of retrieval
                            LocalDateTime.now(),
                            // Currency
                            null);
                    return value;
                })
                .toList();
        */

        List<Value> values = elements.stream()
                .forEach(element -> {
                    Value value = new Value(
                            // Current value of currency
                            Double.parseDouble(element.select("div.currencyItem_actualValueContainer__2xLkB").text().replace(",", ".")),
                            // Time at the date of retrieval
                            LocalDateTime.now(),
                            // Currency
                            null);
                    return element;
                });



        return values;
    }
}
