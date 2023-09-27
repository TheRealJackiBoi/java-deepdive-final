package org.dat3.utils;

import org.dat3.dao.CurrencyDAO;
import org.dat3.dao.ValueDAO;
import org.dat3.config.HibernateConfig;
import org.dat3.model.Currency;
import org.dat3.model.Value;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TradeExtractor {

    public static List<Value> extractData(Elements elements) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("valuta");

        // Instantiate DAOs
        CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
        ValueDAO valueDAO = ValueDAO.getInstance();

        // Set EMFs
        currencyDAO.setEntityManagerFactory(emf);
        valueDAO.setEntityManagerFactory(emf);

        List<Value> values = elements.stream()
                .map(element -> {
                    // Fetches values from string and replaces comma with dot to parse properly to double

                    String doubles = element.select("div.currencyItem_actualValueContainer__2xLkB").text().replace(",", ".");

                    // Creates an array of the strings to make it possible to parse to double by removing the space
                    String[] separateDoubles = doubles.split(" ");

                    // Fetches currency codes
                    String codes = element.select("div.currencyItem_currencySymbolContainer__RKWSP").text();

                    // Fetches Danish names of currencies
                    String names = element.select("div.currencyItem_currencyNameContainer__19YHn").text();

                    // Fetches percentage change in currency ready to be parsed to double if needed
                    String change = element.select("div.currencyItem_currencyChangeContainer__pV3ni").text().replace(",", ".");
                    String[] separateChange = change.split(" ");

                    // Creates objects from the scraped data
                    List<Value> subValues = Arrays.stream(separateDoubles)
                            .map(doubleValue -> {
                                // Assigns a current currency to the object, but if it is not found, creates a new currency
                                Currency currency = currencyDAO.findById(Currency.class, codes);
                                if (currency == null) {
                                    currency = new Currency(names, codes);
                                    currencyDAO.create(currency);
                                }
                                // Create a new value object
                                Value val = new Value(
                                    Double.parseDouble(doubleValue),
                                    LocalDateTime.now());
                                // Add the value object to the currency object
                                currency.addValue(val);
                                // Persist the new currency object
                                valueDAO.create(val);
                                return val;
                            })
                            .collect(Collectors.toList());

                    return subValues;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return values;
    }
}
