package com.mpata.alquileres.controllers;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.mpata.alquileres.models.PropertyFilter;
import com.mpata.alquileres.models.PropertyResponse;
import com.mpata.alquileres.models.enums.Conversion;
import com.mpata.alquileres.models.enums.Currency;
import com.mpata.alquileres.models.enums.NeighborhoodCABA;
import com.mpata.alquileres.models.enums.PropertyType;
import com.mpata.alquileres.models.enums.SurfaceType;
import com.mpata.alquileres.service.PropertyService;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20",required = false) Integer size,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) Currency currency,
            @RequestParam(required = false) SurfaceType surfaceType,
            @RequestParam(required = false) Integer minArea,
            @RequestParam(required = false) Integer maxArea,
            @RequestParam(required = false) Integer minRooms,
            @RequestParam(required = false) Integer maxRooms,
            @RequestParam(required = false) Integer minBedrooms,
            @RequestParam(required = false) Integer maxBedrooms,
            @RequestParam(required = false) Integer minBathrooms,
            @RequestParam(required = false) Integer maxBathrooms,
            @RequestParam(required = false) Integer minGarages,
            @RequestParam(required = false) List<NeighborhoodCABA> neighborhoods,
            @RequestParam(required = false) List<PropertyType> propertyTypes,
            @RequestParam(required = false) Conversion conversion
            ) {
        PropertyFilter filter = new PropertyFilter(
                minPrice,
                maxPrice,
                currency,
                surfaceType,
                minArea,
                maxArea,
                minRooms,
                maxRooms,
                minBedrooms,
                maxBedrooms,
                minBathrooms,
                maxBathrooms,
                minGarages,
                neighborhoods,
                propertyTypes);

        Page<PropertyResponse> properties = service.findAll(filter, conversion, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(properties);
    }

    @PostMapping
    public ResponseEntity<PropertyResponse> filterProperties(@RequestBody PropertyFilter filter,
                                                             @RequestParam(defaultValue = "0") Integer page,
                                                             @RequestParam(defaultValue = "20",required = false) Integer size) {
        //PropertyResponse properties = service.findAll(filter, page, size);
        return ResponseEntity.ok(null);
    }
}
