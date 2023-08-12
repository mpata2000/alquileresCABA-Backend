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

    /**
     * Get the sell value from the dolar api
     * @param conversion: conversion to apply
     * @return the sell value from the dolar api or 0 if the conversion is NONE
     */
    public static BigDecimal getSellValueFromDolarApi(Conversion conversion) {
        if(conversion == null || conversion == Conversion.NONE) {
            return BigDecimal.valueOf(0);
        }
        String url = "https://dolar-api-argentina.vercel.app/v1/dolares/" + conversion.getValue();
        double ventaValue = 0.0;

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

    /**
     * Convert the amount to the currency specified in the conversion parameter
     * @param amount: amount to convert
     * @param currency: currency of the amount
     * @param usdSellRateToArs: sell value of USD
     * @return the converted amount
     */
    public static Long exchangeRateUSDSellValue(Long amount, Currency currency, BigDecimal usdSellRateToArs) {
        BigDecimal priceValue = BigDecimal.valueOf(amount);

        if(currency == Currency.USD) {
            priceValue = priceValue.multiply(usdSellRateToArs);
        } else {
            priceValue = priceValue.divide(usdSellRateToArs, 2, RoundingMode.HALF_UP);
        }

        return priceValue.setScale(0, RoundingMode.HALF_UP).longValue();
    }

    /**
     * Convert the amount to the currency specified in the conversion parameter
     * @param amount: amount to convert
     * @param currency: currency of the amount
     * @param conversion: conversion to apply
     * @return the converted amount
     */
    public static Long exchangeRate(Long amount, Currency currency, Conversion conversion) {
        BigDecimal sellValue = getSellValueFromDolarApi(conversion);
        return exchangeRateUSDSellValue(amount, currency, sellValue);
    }

}
