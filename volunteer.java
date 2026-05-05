package com.cdrn.controller;

import com.cdrn.model.User;
import com.cdrn.model.VolunteerTask;
import com.cdrn.repository.UserRepository;
import com.cdrn.service.VolunteerTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/volunteers")
@RequiredArgsConstructor
@Tag(name = "Volunteers", description = "Volunteer dashboard and task management")
public class VolunteerController {

    private final VolunteerTaskService taskService;
    private final UserRepository userRepository;

    // ---- Task Assignment (Authority only) ----

    @PostMapping("/tasks")
    @PreAuthorize("hasAnyRole('LOCAL_AUTHORITY', 'ADMIN')")
    @Operation(summary = "Create and assign a task to a volunteer")
    public ResponseEntity<VolunteerTask> createTask(
            @RequestBody Map<String, Object> body,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);

        String title = (String) body.get("title");
        String description = (String) body.get("description");
        VolunteerTask.TaskType type = VolunteerTask.TaskType.valueOf(body.get("type").toString());
        VolunteerTask.TaskPriority priority = body.containsKey("priority") ?
                VolunteerTask.TaskPriority.valueOf(body.get("priority").toString()) :
                VolunteerTask.TaskPriority.MEDIUM;
        Double lat = body.containsKey("latitude") ? Double.parseDouble(body.get("latitude").toString()) : null;
        Double lng = body.containsKey("longitude") ? Double.parseDouble(body.get("longitude").toString()) : null;
        String locationDesc = (String) body.get("locationDescription");
        String district = (String) body.get("district");
        Long incidentId = body.containsKey("incidentId") ? Long.parseLong(body.get("incidentId").toString()) : null;
        Long assignedVolunteerId = body.containsKey("assignedVolunteerId") ?
                Long.parseLong(body.get("assignedVolunteerId").toString()) : null;
        LocalDateTime dueAt = body.containsKey("dueAt") ?
                LocalDateTime.parse(body.get("dueAt").toString()) : null;

        VolunteerTask task = taskService.createTask(title, description, type, priority, lat, lng,
                locationDesc, district, incidentId, assignedVolunteerId, userId, dueAt);
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/tasks/{taskId}/assign")
    @PreAuthorize("hasAnyRole('LOCAL_AUTHORITY', 'ADMIN')")
    @Operation(summary = "Assign or reassign a task to a volunteer")
    public ResponseEntity<VolunteerTask> assignTask(
            @PathVariable Long taskId,
            @RequestBody Map<String, Long> body) {
        return ResponseEntity.ok(taskService.assignVolunteer(taskId, body.get("volunteerId")));
    }

    // ---- Volunteer Dashboard ----

    @GetMapping("/tasks/my")
    @PreAuthorize("hasAnyRole('VOLUNTEER', 'LOCAL_AUTHORITY', 'ADMIN')")
    @Operation(summary = "Get my assigned tasks")
    public ResponseEntity<List<VolunteerTask>> getMyTasks(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.getMyTasks(getUserId(userDetails)));
    }

    @GetMapping("/tasks/active")
    @PreAuthorize("hasAnyRole('LOCAL_AUTHORITY', 'ADMIN')")
    @Operation(summary = "Get all active tasks (Authority)")
    public ResponseEntity<List<VolunteerTask>> getActiveTasks() {
        return ResponseEntity.ok(taskService.getActiveTasks());
    }

    @GetMapping("/tasks/unassigned")
    @PreAuthorize("hasAnyRole('LOCAL_AUTHORITY', 'ADMIN')")
    @Operation(summary = "Get unassigned tasks")
    public ResponseEntity<List<VolunteerTask>> getUnassignedTasks() {
        return ResponseEntity.ok(taskService.getUnassignedTasks());
    }

    @PatchMapping("/tasks/{taskId}/status")
    @PreAuthorize("hasAnyRole('VOLUNTEER', 'LOCAL_AUTHORITY', 'ADMIN')")
    @Operation(summary = "Update task status (volunteer updates progress)")
    public ResponseEntity<VolunteerTask> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestBody Map<String, Object> body,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        VolunteerTask.TaskStatus status = VolunteerTask.TaskStatus.valueOf(body.get("status").toString());
        String notes = (String) body.get("notes");
        Double lat = body.containsKey("currentLatitude") ?
                Double.parseDouble(body.get("currentLatitude").toString()) : null;
        Double lng = body.containsKey("currentLongitude") ?
                Double.parseDouble(body.get("currentLongitude").toString()) : null;

        return ResponseEntity.ok(taskService.updateTaskStatus(taskId, status, notes, lat, lng, userId));
    }

    // ---- Volunteer Listing ----

    @GetMapping
    @PreAuthorize("hasAnyRole('LOCAL_AUTHORITY', 'ADMIN')")
    @Operation(summary = "Get all verified volunteers")
    public ResponseEntity<List<User>> getVolunteers(@RequestParam(required = false) String district) {
        List<User> volunteers;
        if (district != null) {
            volunteers = userRepository.findAvailableVolunteersByDistrict(district);
        } else {
            volunteers = userRepository.findByRole(User.UserRole.VOLUNTEER);
        }
        return ResponseEntity.ok(volunteers);
    }

    private Long getUserId(UserDetails userDetails) {
        return userRepository.findByPhone(userDetails.getUsername())
                .map(User::getId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }
}