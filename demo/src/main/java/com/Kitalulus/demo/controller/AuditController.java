package com.Kitalulus.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Kitalulus.demo.dto.responce.BaseResponseDto;
import com.Kitalulus.demo.entity.AuditDetails;
import com.Kitalulus.demo.service.AuditService;

@RestController
@RequestMapping("/audit")
public class AuditController {
    Logger logger = LoggerFactory.getLogger(AuditController.class);
    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping(produces = "application/json")
    public BaseResponseDto<List<AuditDetails>> getAll() {
        logger.info("Country detail api called");
        return BaseResponseDto.success(auditService.getAllAuditDetails());

    }
}
