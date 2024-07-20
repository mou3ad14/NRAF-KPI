package com.example.demo.Controller;

import com.example.demo.Model.JiraIssueNbJours;
import com.example.demo.Service.JiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jira")
@CrossOrigin(origins = "http://localhost:4200")

public class JiraController {

    private final JiraService jiraService;

    @Autowired
    public JiraController(JiraService jiraService) {
        this.jiraService = jiraService;
    }

    @PostMapping("/issues/{issueKey}/fetch")
    public ResponseEntity<String> createIssueData(@PathVariable String issueKey) {
        try {
            jiraService.fetchAndStoreIssueData(issueKey);
            return ResponseEntity.ok("Issue data fetched and stored successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching issue data: " + e.getMessage());
        }
    }

    @GetMapping("/issues/{issueKey}/data")
    public ResponseEntity<JiraIssueNbJours> getIssueData(@PathVariable String issueKey) {
        try {
            JiraIssueNbJours issueData = jiraService.getIssueData(issueKey);
            if (issueData != null) {
                return ResponseEntity.ok(issueData);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
