package com.mpata.alquileres.service;

import com.mpata.alquileres.models.Property;
import com.mpata.alquileres.models.PropertyFilter;
import com.mpata.alquileres.models.PropertyResponse;
import com.mpata.alquileres.models.enums.Conversion;
import com.mpata.alquileres.models.enums.Currency;
import com.mpata.alquileres.models.enums.SortId;
import com.mpata.alquileres.repository.PropertyRepository;

import com.mpata.alquileres.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class PropertyService {
    private static final int PAGE_SIZE = 20;
    @Autowired
    private PropertyRepository repository;

    @Autowired
    private ModelMapper mapper;

    private Pageable createPage(Integer page, SortId sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(page,PAGE_SIZE);
        if (sortBy != null || sortDirection != null) {
            sortBy = Objects.requireNonNullElse(sortBy, SortId.ID);
            pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy.getValue()));
        }
        return pageable;
    }

    private PropertyResponse convertToDto(Property property, BigDecimal conversion) {
        PropertyResponse propertyResponse = mapper.map(property, PropertyResponse.class);
        propertyResponse.setPicsUrls(List.of(property.getPicsUrls().split(",")));

        propertyResponse.setConvertedPrice(Utils.exchangeRateUSDSellValue(property.getPrice(), propertyResponse.getCurrency(), conversion));
        propertyResponse.setConvertedCurrency();

        return propertyResponse;
    }

    public Page<PropertyResponse> findAll(PropertyFilter filter, Conversion conversion, Integer page, SortId sortBy, String sortDirection) {
        Pageable pageable = createPage(page, sortBy, sortDirection);
        Page<Property> properties = repository.findAll(filter.getByFilter(conversion), pageable);
        BigDecimal exchangeRate = Utils.getSellValueFromDolarApi(conversion);
        return properties.map(x -> convertToDto(x, exchangeRate));
    }

    public PropertyResponse increaseClicks(Long id) throws NoSuchElementException {
        Property property = repository.findById(id).orElseThrow();
        property.setClickCount(property.getClickCount() + 1);
        repository.save(property);
        return convertToDto(property, BigDecimal.ONE);
    }
}
