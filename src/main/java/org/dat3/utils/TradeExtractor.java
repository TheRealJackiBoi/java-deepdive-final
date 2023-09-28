package org.dat3.utils;

import jakarta.persistence.EntityManagerFactory;
import org.dat3.config.ExecutorServiceConfig;
import org.dat3.dao.CurrencyDAO;
import org.dat3.dao.ValueDAO;
import org.dat3.config.HibernateConfig;
import org.dat3.model.Currency;
import org.dat3.model.Value;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class TradeExtractor {

    /*
    Bjarki's version
    public static List<Value> extractData(Elements elements) {
        //should probably have EMF as a parameter in method call, so it can easily be changed for the test database in the test class
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("valuta");

        // Instantiate DAOs
        CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
        ValueDAO valueDAO = ValueDAO.getInstance();

        // Set EMFs
        currencyDAO.setEntityManagerFactory(emf);
        valueDAO.setEntityManagerFactory(emf);

        //instantiate Executor service
        ExecutorServiceConfig executorConfig = new ExecutorServiceConfig();
        ExecutorService executorService = executorConfig.getExecutorService();

        List<Value> values = elements.parallelStream()
                .map(element -> executorService.submit(() -> {
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
                }))
                .flatMap(future -> {
                    try {
                        return future.get().stream();
                    }catch (Exception e){
                        e.printStackTrace();
                        return null;
                    }

                })
                .collect(Collectors.toList());
        executorConfig.shutdownExecutorService();

        return values;
    }*/

    public static List<Value> extractData(Elements elements, EntityManagerFactory emf) {

        // Instantiate DAOs
        CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
        ValueDAO valueDAO = ValueDAO.getInstance();

        // Set EMFs
        currencyDAO.setEntityManagerFactory(emf);
        valueDAO.setEntityManagerFactory(emf);

        //instantiate Executor service

        //TODO: not sure what is used for ?
        ExecutorServiceConfig executorConfig = new ExecutorServiceConfig();
        ExecutorService executorService = executorConfig.getExecutorService();

        List<Value> valueList = new ArrayList<>();

        try{
            for(Element div: elements.select("div.currencyItem_currencyItemWrapper__2-TKC")){
                String currencyCode = div.select("div.currencyItem_currencySymbolContainer__RKWSP").text();
                String currencyName = div.select("div.currencyItem_currencyNameContainer__19YHn").text();
                String currencyValue = div.select("div.currencyItem_actualValueContainer__2xLkB").text().replace(",", ".");
                String currencyChange = div.select("div.currencyItem_currencyChangeContainer__pV3ni").text().replace(",", ".");
                String[] separateChange = currencyChange.split(" ");
                String[] separateValue = currencyValue.split(" ");

                Currency currency = currencyDAO.findById(Currency.class, currencyCode);
                if (currency == null) {
                    currency = new Currency(currencyName, currencyCode);
                    currencyDAO.create(currency);
                }

                Value val = new Value(
                        Double.parseDouble(separateValue[0]),
                        LocalDateTime.now());
                currency.addValue(val);
                valueDAO.create(val);
                valueList.add(val);
            }
            executorConfig.shutdownExecutorService();
            return valueList;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
