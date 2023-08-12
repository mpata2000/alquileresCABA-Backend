package com.mpata.alquileres.models;

import com.mpata.alquileres.models.enums.*;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "active_properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Long propertyId;

    @Column(nullable = false, length = 500)
    private String url;

    @Column(name = "prop_type", nullable = false)
    private String propType;

    @Column(nullable = false)
    private long price = 0;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private long expenses = 0;

    @Column(name = "total_area", nullable = false)
    private long totalArea = 0;

    @Column(name = "covered_area", nullable = false)
    private long coveredArea = 0;

    @Column(nullable = false)
    private int rooms = 0;

    @Column(nullable = false)
    private int bedrooms = 0;

    @Column(nullable = false)
    private int bathrooms = 0;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private int garage = 0;

    @Column(nullable = false)
    private String page;

    @Column(name = "pics_urls")
    private String picsUrls;

    @Column(name = "first_read_date", columnDefinition = "DATETIME")
    private LocalDateTime firstReadDate;

    @Column(name = "last_read_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastReadDate;

    @Column(name = "click_count")
    private int clickCount;


}
