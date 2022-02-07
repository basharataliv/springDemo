package com.Kitalulus.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Kitalulus.demo.entity.AuditDetails;
import com.Kitalulus.demo.repository.AuditRepository;
import com.Kitalulus.demo.service.AuditService;

@Service
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepo;

    public AuditServiceImpl(AuditRepository auditRepo) {
        this.auditRepo = auditRepo;
    }

    @Override
    public AuditDetails save(AuditDetails details) {
        return auditRepo.save(details);
    }

    @Override
    public List<AuditDetails> getAllAuditDetails() {
        return auditRepo.findAll();
    }
}
