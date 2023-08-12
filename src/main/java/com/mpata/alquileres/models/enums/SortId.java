package com.mpata.alquileres.models.enums;

public enum SortId {
    PRICE("price"),
    ID("propertyId"),
    CLICKS("clickCount");

    private final String value;

    private SortId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
