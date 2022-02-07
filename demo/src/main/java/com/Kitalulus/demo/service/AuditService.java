package com.Kitalulus.demo.service;

import java.util.List;

import com.Kitalulus.demo.entity.AuditDetails;

public interface AuditService {

    AuditDetails save(AuditDetails details);

    List<AuditDetails> getAllAuditDetails();

}
