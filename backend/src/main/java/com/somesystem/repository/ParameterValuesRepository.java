package com.somesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.somesystem.entity.ParameterValue;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ParameterValuesRepository extends JpaRepository <ParameterValue, Long> {
    List<ParameterValue> findAllByParametrasId(Long id);
    @Transactional
    void deleteAllByParametrasId(long id);

    Optional<ParameterValue> findByParametrasId(Long id);
}
