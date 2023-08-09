package com.mpata.alquileres.models;

import com.mpata.alquileres.models.enums.*;
import jakarta.persistence.criteria.Predicate;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PropertyFilter {
    private Long minPrice;
    private Long maxPrice;
    private Currency currency;
    private SurfaceType surfaceType;
    private Integer minArea;
    private Integer maxArea;
    private Integer minRooms;
    private Integer maxRooms;
    private Integer minBedrooms;
    private Integer maxBedrooms;
    private Integer minBathrooms;
    private Integer maxBathrooms;
    private Integer minGarages;
    private List<NeighborhoodCABA> neighborhoods;
    private List<PropertyType> propertyTypes;

    public Specification<Property> getByFilter(){
        return (((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            if(minPrice != null || maxPrice != null){
                String currency = this.currency == null ? Currency.ARS.toString() : this.currency.toString();
                predicates.add(criteriaBuilder.equal(root.get("currency"), currency));
            }
            String surfaceType = this.surfaceType == null ? SurfaceType.TOTAL.getValue() : this.surfaceType.getValue();
            if (minArea != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(surfaceType), minArea));
            }
            if (maxArea != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(surfaceType), maxArea));
            }
            if (minRooms != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rooms"), minRooms));
            }
            if (maxRooms != null && maxRooms > 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rooms"), maxRooms));
            }
            if (minBedrooms != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bedrooms"), minBedrooms));
            }
            if (maxBedrooms != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("bedrooms"), maxBedrooms));
            }
            if (minBathrooms != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bathrooms"), minBathrooms));
            }
            if (maxBathrooms != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("bathrooms"), maxBathrooms));
            }
            if (minGarages != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("garages"), minGarages));
            }
            if (neighborhoods != null && !neighborhoods.isEmpty()) {
                List<String> n = neighborhoods.stream().map(NeighborhoodCABA::getDisplayName).toList();
                predicates.add(criteriaBuilder.in(root.get("neighborhood")).value(n));
            }
            if (propertyTypes != null && !propertyTypes.isEmpty()) {
                predicates.add(criteriaBuilder.in(root.get("prop_type")).value(propertyTypes));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }));
    }
}
