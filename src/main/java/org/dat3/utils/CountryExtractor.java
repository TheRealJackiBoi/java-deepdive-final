package org.dat3.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.dat3.model.Country;

import java.util.List;

public class CountryExtractor {

        public static String extract(String jsonStr) {
            String result = null;
            try {
                Country countries = gson.fromJson(jsonStr, Country.class);
                //result = obj.getString("country");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
}
