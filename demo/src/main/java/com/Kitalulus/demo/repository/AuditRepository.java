package com.Kitalulus.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Kitalulus.demo.entity.AuditDetails;

@Repository
public interface AuditRepository extends JpaRepository<AuditDetails, Long> {

}
