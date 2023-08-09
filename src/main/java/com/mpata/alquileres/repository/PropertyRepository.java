package com.mpata.alquileres.repository;

import com.mpata.alquileres.models.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    Page<Property> findAll(Specification<Property> filter, Pageable pageable);
}
