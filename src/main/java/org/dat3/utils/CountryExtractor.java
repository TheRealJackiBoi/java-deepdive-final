package org.dat3.utils;

import com.google.gson.*;
import org.dat3.config.ExecutorConfig;
import org.dat3.dao.CountryDAO;
import org.dat3.dao.CurrencyDAO;
import org.dat3.model.Country;
import org.dat3.model.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CountryExtractor {

        public static List<Country> extract(String jsonStr) {
            List<Country> countries = new ArrayList<>();

            CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
            CountryDAO countryDAO = CountryDAO.getInstance();

            try {
                JsonArray obj = gson.fromJson(jsonStr, JsonArray.class);

                ExecutorConfig executorConfig = new ExecutorConfig();
                ExecutorService executorService = executorConfig.getExecutorService();
                AtomicInteger completedTasks = new AtomicInteger(0);

                for (JsonElement jsonElement : obj) {
                    executorService.submit(() -> {

                    JsonObject jObject = jsonElement.getAsJsonObject();

                    //values
                    String name = jObject.get("name").getAsJsonObject().get("common").getAsString();

                    //Check if country already exists
                    if (countryDAO.findById(Country.class, name) != null) {
                        continue;
                    }

                    String capital = jObject.get("capital").getAsString();
                    double area = jObject.get("area").getAsDouble();
                    int population = jObject.get("population").getAsInt();
                    String cca3 = jObject.get("cca3").getAsString();

                    //currency
                    JsonObject currencyJson = jObject.get("currency").getAsJsonObject();
                    Currency currency = currencyDAO.findById(Currency.class, currencyJson.get("code").getAsString());

                    if (currency == null) {
                        currency = new Currency(currencyJson.get("code").getAsString(), currencyJson.get("name").getAsString());
                        currencyDAO.create(currency);
                    }

                    //country
                    Country country = new Country(name, capital, area, population, cca3);
                    currency.addCountry(country);
                    countryDAO.create(country);

                    countries.add(country);

                    completedTasks.incrementAndGet();

                    });
                }
                executorConfig.shutdownExecutorService();
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

                while (completedTasks.get() < obj.size()){
                    Thread.sleep(100);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return countries;
        }

    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
}
