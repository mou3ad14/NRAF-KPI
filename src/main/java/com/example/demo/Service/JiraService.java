package com.example.demo.Service;

import com.example.demo.Model.JiraIssueNbJours;
import com.example.demo.Repository.JiraIssueNbJoursRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JiraService {
    private final RestTemplate restTemplate;
    private final String jiraUrl;
    private final String jiraUsername;
    private final String jiraToken;
    private final JiraIssueNbJoursRepository jiraIssueRepository;

    @Autowired
    public JiraService(RestTemplate restTemplate,
                       @Value("${jira.url}") String jiraUrl,
                       @Value("${jira.username}") String jiraUsername,
                       @Value("${jira.token}") String jiraToken,
                       JiraIssueNbJoursRepository jiraIssueRepository) {
        this.restTemplate = restTemplate;
        this.jiraUrl = jiraUrl;
        this.jiraUsername = jiraUsername;
        this.jiraToken = jiraToken;
        this.jiraIssueRepository = jiraIssueRepository;
    }

    public void fetchAndStoreIssueData(String issueKey) {
            try {
                String url = jiraUrl + "/rest/api/3/issue/" + issueKey;
                HttpHeaders headers = new HttpHeaders();
                headers.setBasicAuth(jiraUsername, jiraToken);
                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(response.getBody());


                    System.out.println("Jira Response: " + root.toString());

                    JsonNode fieldsNode = root.path("fields");
                    String description = fieldsNode.path("summary").asText("");
                    double storyPoints = fieldsNode.path("customfield_10016").asDouble(0.0);
                    System.out.println("waaaaa oussama lghzal"+description);
                    JiraIssueNbJours jiraIssue = new JiraIssueNbJours(issueKey, description, storyPoints);
                    jiraIssueRepository.save(jiraIssue);

                    System.out.println(jiraIssue.getDescription());
                } else {
                    throw new RuntimeException("Failed to fetch issue from Jira. Status: " + response.getStatusCode());
                }
            } catch (Exception e) {
                throw new RuntimeException("Error fetching or processing issue from Jira: " + e.getMessage(), e);
            }
    }

    public JiraIssueNbJours getIssueData(String issueKey) {
        return jiraIssueRepository.findByIssueKey(issueKey).orElse(null);
    }
}
