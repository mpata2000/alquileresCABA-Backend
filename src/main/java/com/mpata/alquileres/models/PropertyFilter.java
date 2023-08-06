package com.mpata.alquileres.models;

import com.mpata.alquileres.models.enums.Conversion;
import com.mpata.alquileres.models.enums.Currency;
import com.mpata.alquileres.models.enums.NeighborhoodCABA;
import com.mpata.alquileres.models.enums.PropertyType;
import lombok.Data;

import java.util.List;

@Data
public class PropertyFilter {
    private long minPrice;
    private long maxPrice;
    private Currency currency;
    private int expenses;
    private int minTotalArea;
    private int maxTotalArea;
    private int minCoveredArea;
    private int maxCoveredArea;
    private int minRooms;
    private int maxRooms;
    private int minBedrooms;
    private int maxBedrooms;
    private int minBathrooms;
    private int maxBathrooms;
    private int minGarages;
}
