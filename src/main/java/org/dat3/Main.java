package org.dat3;

import org.dat3.utils.CountryFetcher;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bjaaark er iron 2!");

        String countryApiUrl = "https://restcountries.com/v3.1/currency/";

        System.out.println(CountryFetcher.fetch(countryApiUrl, "EUR"));
    }
}