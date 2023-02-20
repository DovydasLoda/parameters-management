package com.somesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.somesystem.entity.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository <Parameter, Long> {
}
