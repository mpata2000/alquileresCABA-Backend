package com.mpata.alquileres.controllers;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.mpata.alquileres.models.PropertyFilter;
import com.mpata.alquileres.models.PropertyResponse;
import com.mpata.alquileres.models.enums.Conversion;
import com.mpata.alquileres.models.enums.Currency;
import com.mpata.alquileres.models.enums.NeighborhoodCABA;
import com.mpata.alquileres.models.enums.PropertyType;
import com.mpata.alquileres.service.PropertyService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/properties")
public class PropertiesController {

    @Autowired
    private PropertyService service;

    @GetMapping("/hello")
    public String getProperties() {
        return "Hello World";
    }

    @Operation(summary = "Get all properties with pagination")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Properties found successfully!")
            }
    )
    @GetMapping
    public ResponseEntity<Page<PropertyResponse>> getProperties(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") Pageable pageable,
            @RequestParam(defaultValue = "20",required = false) int limit,
            @RequestParam(required = false) long minPrice,
            @RequestParam(required = false) long maxPrice,
            @RequestParam(required = false) Currency currency,
            @RequestParam(required = false) int expenses,
            @RequestParam(required = false) int minTotalArea,
            @RequestParam(required = false) int maxTotalArea,
            @RequestParam(required = false) int minCoveredArea,
            @RequestParam(required = false) int maxCoveredArea,
            @RequestParam(required = false) int minRooms,
            @RequestParam(required = false) int maxRooms,
            @RequestParam(required = false) int minBedrooms,
            @RequestParam(required = false) int maxBedrooms,
            @RequestParam(required = false) int minBathrooms,
            @RequestParam(required = false) int maxBathrooms,
            @RequestParam(required = false) int minGarages,
            @RequestParam(required = false) List<NeighborhoodCABA> neighborhoods,
            @RequestParam(required = false) List<PropertyType> propertyTypes,
            @RequestParam(required = false) Conversion conversion
            ) {
        PropertyFilter filter = PropertyFilter.builder()
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .currency(currency)
                .expenses(expenses)
                .minTotalArea(minTotalArea)
                .maxTotalArea(maxTotalArea)
                .minCoveredArea(minCoveredArea)
                .maxCoveredArea(maxCoveredArea)
                .minRooms(minRooms)
                .maxRooms(maxRooms)
                .minBedrooms(minBedrooms)
                .maxBedrooms(maxBedrooms)
                .minBathrooms(minBathrooms)
                .maxBathrooms(maxBathrooms)
                .minGarages(minGarages)
                .build();
        Page<PropertyResponse> properties = service.findAll(page, limit);
        return ResponseEntity.ok(properties);
    }
}
