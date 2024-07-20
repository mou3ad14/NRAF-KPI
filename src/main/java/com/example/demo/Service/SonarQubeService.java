package com.example.demo.Service;

import com.example.demo.Model.SonarQubeMetrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Service
public class SonarQubeService {
    private final WebClient webClient;

    public SonarQubeService(@Value("${sonarqube.api.url}") String apiUrl, @Value("${sonarqube.api.token}") String apiToken) {
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((apiToken + ":").getBytes()))
                .build();
    }

    public Mono<SonarQubeMetrics> getProjectMetrics(String projectKey) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/measures/component")
                        .queryParam("component", projectKey)
                        .queryParam("metricKeys", "bugs,vulnerabilities,code_smells,reliability_remediation_effort,security_hotspots_reviewed")
                        .build())
                .retrieve()
                .bodyToMono(SonarQubeMetrics.class);
    }
}
