package org.dat3.utils;

import jakarta.persistence.EntityManagerFactory;
import org.dat3.model.Value;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TradeExtractor {

    public static List<Value> extractData(Elements elements) {

        // check om currency eksisterer, ellers opret en
        // returner en value


        //EntityManagerFactory emf = HibernateConfig.createEntityManagerFactoryConfig("DBNAME");

        List<Value> values = elements.stream()
                .map(element -> {
                    // Fetches values from string and replaces comma with dot to parse properly to double
                    String text = element.select("div.currencyItem_actualValueContainer__2xLkB").text().replace(",", ".");

                    // Creates an array of the strings to make it possible to parse to double by removing the space
                    String[] separateDoubles = text.split(" ");



                    // Creates a separate object for each value
                    List<Value> subValues = Arrays.stream(separateDoubles)
                            .map(doubleValue -> new Value(
                                    Double.parseDouble(doubleValue),
                                    LocalDateTime.now(),
                                    //LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))),
                                    null))
                            .collect(Collectors.toList());

                    return subValues;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return values;
    }
}