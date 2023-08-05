package com.mpata.alquileres.service;

import com.mpata.alquileres.models.Property;
import com.mpata.alquileres.models.PropertyResponse;
import com.mpata.alquileres.repository.PropertyRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository repository;

    @Autowired
    private ModelMapper mapper;

    private PropertyResponse convertToDto(Property property) {
        PropertyResponse propertyResponse = mapper.map(property, PropertyResponse.class);
        propertyResponse.setPicsUrls(List.of(property.getPicsUrls().split(",")));
        return propertyResponse;
    }

    public Page<PropertyResponse> findAll(int page, int limit) {
        Page<Property> properties = repository.findAll(PageRequest.of(page, limit));
        return properties.map(this::convertToDto);
    }
}
