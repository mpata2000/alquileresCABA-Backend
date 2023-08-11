package com.mpata.alquileres.models;

import com.mpata.alquileres.models.enums.*;
import com.mpata.alquileres.utils.Utils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class PropertyFilter {
    private long minPrice = 0L;
    private long maxPrice = 0L;
    private Currency currency = Currency.ARS;
    private SurfaceType surfaceType = SurfaceType.COVERED;
    private int minArea = 0;
    private int maxArea = 0;
    private int minRooms = 0;
    private int maxRooms = 0;
    private int minBedrooms = 0;
    private int maxBedrooms = 0;
    private int minBathrooms = 0;
    private int maxBathrooms = 0;
    private int minGarages = 0;
    private List<NeighborhoodCABA> neighborhoods;
    private List<PropertyType> propertyTypes;

    public PropertyFilter(Long minPrice, Long maxPrice, Currency currency, SurfaceType surfaceType, Integer minArea, Integer maxArea, Integer minRooms, Integer maxRooms, Integer minBedrooms, Integer maxBedrooms, Integer minBathrooms, Integer maxBathrooms, Integer minGarages, List<NeighborhoodCABA> neighborhoods, List<PropertyType> propertyTypes) {
        this.minPrice = minPrice == null ? 0 : minPrice;
        this.maxPrice = maxPrice == null ? 0 : maxPrice;
        this.currency = currency == null ? Currency.ARS : currency;
        this.surfaceType = surfaceType == null ? SurfaceType.COVERED : surfaceType;
        this.minArea = minArea == null ? 0 : minArea;
        this.maxArea = maxArea == null ? 0 : maxArea;
        this.minRooms = minRooms == null ? 0 : minRooms;
        this.maxRooms = maxRooms == null ? 0 : maxRooms;
        this.minBedrooms = minBedrooms == null ? 0 : minBedrooms;
        this.maxBedrooms = maxBedrooms == null ? 0 : maxBedrooms;
        this.minBathrooms = minBathrooms == null ? 0 : minBathrooms;
        this.maxBathrooms = maxBathrooms == null ? 0 : maxBathrooms;
        this.minGarages = minGarages == null ? 0 : minGarages;
        this.neighborhoods = neighborhoods;
        this.propertyTypes = propertyTypes;
    }

    private List<Predicate> getPricePredicates(Root<Property> root, CriteriaBuilder criteriaBuilder, Long minPrice, Long maxPrice, Currency currency) {
        List<Predicate> predicates = new ArrayList<>();

        if (minPrice > 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice > minPrice) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        if(minPrice > 0 || maxPrice > 0){
            String s = currency == null ? Currency.ARS.toString() : currency.toString();
            predicates.add(criteriaBuilder.equal(root.get("currency"), s));
        }

        return predicates;
    }

    private List<Predicate> getBasicPredicates(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        String surfaceType = this.surfaceType == null ? SurfaceType.TOTAL.getValue() : this.surfaceType.getValue();
        if (minArea > 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(surfaceType), minArea));
        }
        if (maxArea > minArea) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(surfaceType), maxArea));
        }
        if (minRooms > 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rooms"), minRooms));
        }
        if (maxRooms > minRooms) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rooms"), maxRooms));
        }

        if (minBedrooms > 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bedrooms"), minBedrooms));
        }
        if (maxBedrooms > minBedrooms) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("bedrooms"), maxBedrooms));
        }

        if (minBathrooms > 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bathrooms"), minBathrooms));
        }
        if (maxBathrooms > minBathrooms) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("bathrooms"), maxBathrooms));
        }

        if (minGarages > 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("garage"), minGarages));
        }

        if (neighborhoods != null && !neighborhoods.isEmpty()) {

            predicates.add(root.get("neighborhood").in(neighborhoods.stream().map(NeighborhoodCABA::getValue).toArray()));
        }

        if (propertyTypes != null && !propertyTypes.isEmpty()) {
            predicates.add(root.get("propType").in(propertyTypes));
        }

        return predicates;
    }


    public Specification<Property> getByFilter(Conversion conversion){
        return (((root, query, criteriaBuilder) -> {
            List<Predicate> basicPred = getBasicPredicates(root, query, criteriaBuilder);
            if(minPrice == 0 && maxPrice == 0){
                return criteriaBuilder.and(basicPred.toArray(new Predicate[0]));
            }

            List<Predicate> preds = getPricePredicates(root, criteriaBuilder, minPrice, maxPrice, currency);
            preds.addAll(basicPred);

            if(conversion == null || conversion == Conversion.NONE){
                return criteriaBuilder.and(preds.toArray(new Predicate[0]));
            }

            Long minPriceConverted = Utils.convertPrice(minPrice, currency, conversion);
            Long maxPriceConverted = Utils.convertPrice(maxPrice, currency, conversion);

            Currency currencyConverted = currency == Currency.ARS ? Currency.USD : Currency.ARS;
            List<Predicate> predsConverted = getPricePredicates(root, criteriaBuilder, minPriceConverted, maxPriceConverted, currencyConverted );
            predsConverted.addAll(getBasicPredicates(root, query, criteriaBuilder));

            return criteriaBuilder.or(
                    criteriaBuilder.and(preds.toArray(new Predicate[0])),
                    criteriaBuilder.and(predsConverted.toArray(new Predicate[0]))
            );
        }));
    }

}
