package com.mpata.alquileres.service;

import com.mpata.alquileres.models.Property;
import com.mpata.alquileres.models.PropertyFilter;
import com.mpata.alquileres.models.PropertyResponse;
import com.mpata.alquileres.models.enums.Conversion;
import com.mpata.alquileres.models.enums.NeighborhoodCABA;
import com.mpata.alquileres.models.enums.PropertyType;
import com.mpata.alquileres.repository.PropertyRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository repository;

    @Autowired
    private ModelMapper mapper;

    private Pageable createPage(Integer page, Integer size, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size);
        if (sortBy != null && sortDirection != null) {
            pageable = PageRequest.of(page, size, Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        }
        return pageable;
    }

    private PropertyResponse convertToDto(Property property) {
        PropertyResponse propertyResponse = mapper.map(property, PropertyResponse.class);
        propertyResponse.setPicsUrls(List.of(property.getPicsUrls().split(",")));
        return propertyResponse;
    }

    public Page<PropertyResponse> findAll(Pageable pageable) {
        Page<Property> properties = repository.findAll(pageable);
        return properties.map(this::convertToDto);
    }

    public Page<PropertyResponse> findAll(PropertyFilter filter, List<NeighborhoodCABA> neighborhoods, List<PropertyType> propertyTypes, Conversion conversion, Integer page, Integer size, String sortBy, String sortDirection) {
        Pageable pageable = createPage(page, size, sortBy, sortDirection);
        Page<Property> properties = repository.findAll(filter.getByFilter(), pageable);
        return properties.map(this::convertToDto);
    }
}
