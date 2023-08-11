package com.mpata.alquileres.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mpata.alquileres.models.enums.Conversion;
import com.mpata.alquileres.models.enums.Currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {
    private static BigDecimal getVentaValueFromUrl(String url) {
        Double ventaValue = 0.0;

        try {
            // Make an HTTP GET request
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();

                // Get the "venta" value
                ventaValue = jsonObject.get("venta").getAsDouble();
            } else {
                System.out.println("HTTP request failed with response code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return BigDecimal.valueOf(ventaValue);
    }

    public static Long convertPrice(Long price, Currency currency, Conversion conversion) {
        BigDecimal priceValue = BigDecimal.valueOf(price);
        BigDecimal ventaValue = getVentaValueFromUrl("https://dolar-api-argentina.vercel.app/v1/dolares/" + conversion.getValue());

        if(currency == Currency.USD) {
            priceValue = priceValue.multiply(ventaValue);
        } else {
            priceValue = priceValue.divide(ventaValue, 2, RoundingMode.HALF_UP);
        }

        return priceValue.setScale(0, RoundingMode.HALF_UP).longValue();
    }

}
