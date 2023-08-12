package com.mpata.alquileres.controllers;


import com.mpata.alquileres.models.PropertyFilter;
import com.mpata.alquileres.models.PropertyResponse;
import com.mpata.alquileres.models.enums.*;
import com.mpata.alquileres.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.Min;

import java.util.List;

@RestController
@RequestMapping("api/v1/properties")
public class PropertiesController {

    @Autowired
    private PropertyService service;

    @Operation(summary = "Get all properties with pagination")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Properties found successfully!")
            }
    )
    @GetMapping
    public ResponseEntity<Page<PropertyResponse>> getProperties(
            @RequestParam(defaultValue = "0")  Integer page,
            @RequestParam(required = false) @Min(0) String sortDirection,
            @RequestParam(required = false) @Min(0) SortId sortBy,
            @RequestParam(required = false) @Min(0) Long minPrice,
            @RequestParam(required = false) @Min(0) Long maxPrice,
            @RequestParam(required = false) Currency currency,
            @RequestParam(required = false) SurfaceType surfaceType,
            @RequestParam(required = false) @Min(0) Integer minArea,
            @RequestParam(required = false) @Min(0) Integer maxArea,
            @RequestParam(required = false) @Min(0) Integer minRooms,
            @RequestParam(required = false) @Min(0) Integer maxRooms,
            @RequestParam(required = false) @Min(0) Integer minBedrooms,
            @RequestParam(required = false) @Min(0) Integer maxBedrooms,
            @RequestParam(required = false) @Min(0) Integer minBathrooms,
            @RequestParam(required = false) @Min(0) Integer maxBathrooms,
            @RequestParam(required = false) @Min(0) Integer minGarages,
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

        Page<PropertyResponse> properties = service.findAll(filter, conversion, page, sortBy, sortDirection);
        return ResponseEntity.ok(properties);
    }

    @PostMapping
    public ResponseEntity<PropertyResponse> filterProperties(@RequestBody PropertyFilter filter,
                                                             @RequestParam(defaultValue = "0") Integer page,
                                                             @RequestParam(defaultValue = "20",required = false) @Min(0) Integer size) {
        //PropertyResponse properties = service.findAll(filter, page, size);
        return ResponseEntity.ok(null);
    }

    @Transactional
    @PatchMapping("increase-clicks/{id}")
    public ResponseEntity<PropertyResponse> increaseClicks(@PathVariable Long id) {
        try{
            PropertyResponse property = service.increaseClicks(id);
            return ResponseEntity.ok(property);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
