package com.mpata.alquileres.models.enums;

public enum Conversion {
    NONE(""),
    DOLAR_MEP("bolsa"),
    DOLAR_BLUE("blue");

    private final String value;

    private Conversion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
