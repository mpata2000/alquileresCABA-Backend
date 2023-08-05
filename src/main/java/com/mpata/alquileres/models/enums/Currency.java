package com.mpata.alquileres.models.enums;

import java.util.Arrays;
import java.util.List;

public enum Currency {
    ARS,
    USD;

    static public Currency fromString(String currency) {
        List<String> usd = Arrays.asList("USD", "U$S", "DOLARES", "DOLAR", "DÓLARES", "DÓLAR");
        if (usd.contains(currency.toUpperCase())) {
            return Currency.USD;
        } else {
            return Currency.ARS;
        }
    }
}
