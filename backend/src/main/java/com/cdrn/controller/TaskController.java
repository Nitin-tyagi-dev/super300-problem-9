package com.cdrn.controller;
//cdrn project
import com.cdrn.model.Enums;
import com.cdrn.model.Task;
import com.cdrn.repository.TaskRepository;
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
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> list(@RequestParam(required = false) String userId) {
        if (userId != null && !userId.isBlank()) {
            return taskRepository.findByAssignedToUserId(userId);
        }
        return taskRepository.findAll();
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        if (task.getStatus() == null) {
            task.setStatus(Enums.TaskStatus.PENDING);
        }
        if (task.getAssignedAt() == null) {
            task.setAssignedAt(LocalDateTime.now());
        }
        return taskRepository.save(task);
    }

    @PatchMapping("/{id}/accept")
    public ResponseEntity<?> accept(@PathVariable String id, @RequestBody Map<String, String> body) {
        return taskRepository.findById(id)
                .map(task -> {
                    String userId = body.get("userId");
                    if (userId == null || userId.isBlank()) {
                        return ResponseEntity.badRequest().body(Map.of("message", "userId is required"));
                    }
                    task.setAssignedToUserId(userId);
                    task.setStatus(Enums.TaskStatus.ACCEPTED);
                    return ResponseEntity.ok(taskRepository.save(task));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable String id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setStatus(Enums.TaskStatus.COMPLETED);
                    task.setCompletedAt(LocalDateTime.now());
                    return ResponseEntity.ok(taskRepository.save(task));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
