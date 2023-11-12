package org.documentoviscode.splashyapi.controllers;

import io.camunda.tasklist.exception.TaskListException;
import lombok.AllArgsConstructor;
import org.documentoviscode.splashyapi.services.DocumentCirculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class DocumentCirculationController {

    private final DocumentCirculationService documentCirculationService;

    @PostMapping("/create")
    public ResponseEntity<String> createInstance() {
        documentCirculationService.createProcessInstance();
        return ResponseEntity.ok("200 okiej");
    }

    @PostMapping("/activityReview")
    public ResponseEntity<String> completeActivityReview() {
        try {
            documentCirculationService.completeActivityReviewTask();
        } catch ( TaskListException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("200 okiej");
    }
}
