package com.mpata.alquileres.models;

import com.mpata.alquileres.models.enums.Currency;
import com.mpata.alquileres.models.enums.Page;
import com.mpata.alquileres.models.enums.PropertyType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyResponse {
    private Long propertyId;
    private String url;
    private PropertyType propType;
    private long price;
    private Currency currency;
    private long expenses;
    private long totalArea;
    private long coveredArea;
    private int rooms;
    private int bedrooms;
    private int bathrooms;
    private String address;
    private String neighborhood;
    private int garage;
    private Page page;
    private List<String> picsUrls;
    private LocalDateTime lastReadDate;
}
