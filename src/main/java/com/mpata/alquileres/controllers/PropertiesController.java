package com.mpata.alquileres.controllers;

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
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "20",required = false) int limit,
            @ModelAttribute PropertyFilter filter,
            @RequestParam(required = false) List<NeighborhoodCABA> neighborhoods,
            @RequestParam(required = false) List<PropertyType> propertyTypes,
            @RequestParam(required = false) Conversion conversion
            ) {
        Page<PropertyResponse> properties = service.findAll(page-1, limit);
        return ResponseEntity.ok(properties);
    }
}
