package com.mpata.alquileres.models.enums;

public enum PropertyType {
    DEPARTAMENTO,
    CASA,
    PH;

    public static PropertyType fromString(String propType) {
        return switch (propType.toUpperCase()) {
            case "CASA" -> PropertyType.CASA;
            case "PH" -> PropertyType.PH;
            default -> PropertyType.DEPARTAMENTO;
        };
    }
}
