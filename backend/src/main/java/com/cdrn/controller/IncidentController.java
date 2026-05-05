package com.cdrn.controller;

import com.cdrn.model.Enums;
import com.cdrn.model.Incident;
import com.cdrn.repository.IncidentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/incidents")
public class IncidentController {

    private final IncidentRepository incidentRepository;

    public IncidentController(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @GetMapping
    public List<Incident> list(
            @RequestParam(required = false) String district,
            @RequestParam(required = false) Enums.IncidentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        if (district != null && status != null) {
            return incidentRepository.findByStatusAndDistrict(status, district);
        }
        if (district != null) {
            return incidentRepository.findByDistrict(district, PageRequest.of(page, size));
        }
        if (status != null) {
            return incidentRepository.findByStatus(status);
        }
        return incidentRepository.findAll();
    }

    @PostMapping
    public Incident create(@RequestBody Incident incident) {
        LocalDateTime now = LocalDateTime.now();
        if (incident.getReportedAt() == null) {
            incident.setReportedAt(now);
        }
        incident.setUpdatedAt(now);
        if (incident.getStatus() == null) {
            incident.setStatus(Enums.IncidentStatus.REPORTED);
        }
        return incidentRepository.save(incident);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestBody Map<String, String> body) {
        return incidentRepository.findById(id)
                .map(existing -> {
                    String statusValue = body.get("status");
                    if (statusValue == null) {
                        return ResponseEntity.badRequest().body(Map.of("message", "status is required"));
                    }
                    existing.setStatus(Enums.IncidentStatus.valueOf(statusValue));
                    existing.setUpdatedAt(LocalDateTime.now());
                    if (existing.getStatus() == Enums.IncidentStatus.RESOLVED && existing.getResolvedAt() == null) {
                        existing.setResolvedAt(LocalDateTime.now());
                    }
                    return ResponseEntity.ok(incidentRepository.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
