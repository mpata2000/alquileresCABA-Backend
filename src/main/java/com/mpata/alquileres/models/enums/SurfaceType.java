package com.mpata.alquileres.models.enums;

public enum SurfaceType {
    TOTAL("total_area"),
    COVERED("covered_area");

    private final String value;

    private SurfaceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
