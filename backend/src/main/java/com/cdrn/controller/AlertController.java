package com.cdrn.controller;

import com.cdrn.model.Alert;
import com.cdrn.repository.AlertRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/alerts")
public class AlertController {

    private final AlertRepository alertRepository;

    public AlertController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @GetMapping
    public List<Alert> list(@RequestParam(required = false) String district) {
        if (district != null && !district.isBlank()) {
            return alertRepository.findByTargetDistrictsContaining(district);
        }
        return alertRepository.findAll().stream()
                .sorted(Comparator.comparing(Alert::getIssuedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }

    @GetMapping("/active")
    public List<Alert> active() {
        return alertRepository.findActiveAlerts(LocalDateTime.now()).stream()
                .sorted(Comparator.comparing(Alert::getIssuedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }

    @PostMapping
    public Alert create(@RequestBody Alert alert) {
        if (alert.getIssuedAt() == null) {
            alert.setIssuedAt(LocalDateTime.now());
        }
        if (alert.getExpiresAt() == null) {
            alert.setExpiresAt(LocalDateTime.now().plusDays(1));
        }
        return alertRepository.save(alert);
    }
}
