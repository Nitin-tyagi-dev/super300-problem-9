package com.cdrn.controller;

import com.cdrn.model.SosRequest;
import com.cdrn.model.User;
import com.cdrn.repository.UserRepository;
import com.cdrn.service.SosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/sos")
@RequiredArgsConstructor
@Tag(name = "SOS", description = "Emergency SOS requests")
public class SosController {

    private final SosService sosService;
    private final UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Send SOS distress signal with GPS location")
    public ResponseEntity<SosRequest> sendSos(
            @RequestBody Map<String, Object> body,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        Double lat = Double.parseDouble(body.get("latitude").toString());
        Double lng = Double.parseDouble(body.get("longitude").toString());
        SosRequest.SosType type = SosRequest.SosType.valueOf(body.get("type").toString());
        Integer personsCount = body.containsKey("personsCount") ?
                Integer.parseInt(body.get("personsCount").toString()) : null;
        String info = (String) body.get("additionalInfo");
        String contact = (String) body.get("contactNumber");
        String locationDesc = (String) body.get("locationDescription");

        SosRequest sos = sosService.sendSos(userId, lat, lng, type, personsCount, info, contact, locationDesc);
        return ResponseEntity.ok(sos);
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('LOCAL_AUTHORITY', 'ADMIN', 'VOLUNTEER')")
    @Operation(summary = "Get all active SOS requests (Authorities/Volunteers)")
    public ResponseEntity<List<SosRequest>> getActiveSos() {
        return ResponseEntity.ok(sosService.getActiveSosRequests());
    }

    @GetMapping("/my")
    @Operation(summary = "Get my SOS requests")
    public ResponseEntity<List<SosRequest>> getMySos(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(sosService.getMySosRequests(getUserId(userDetails)));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('LOCAL_AUTHORITY', 'ADMIN', 'VOLUNTEER')")
    @Operation(summary = "Update SOS status (Authorities)")
    public ResponseEntity<SosRequest> updateSosStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        SosRequest.SosStatus status = SosRequest.SosStatus.valueOf(body.get("status").toString());
        Long teamId = body.containsKey("teamId") ? Long.parseLong(body.get("teamId").toString()) : null;
        String notes = (String) body.get("notes");
        return ResponseEntity.ok(sosService.updateSosStatus(id, status, teamId, notes));
    }

    private Long getUserId(UserDetails userDetails) {
        return userRepository.findByPhone(userDetails.getUsername())
                .map(User::getId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }
}