package com.example.demo.Controller;


import com.example.demo.Model.SonarQubeMetrics;
import com.example.demo.Service.SonarQubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SonarQubeController {
    @Autowired
    private SonarQubeService sonarQubeService;

    @GetMapping("/metrics")
    public Mono<SonarQubeMetrics> getMetrics(@RequestParam String projectKey) {
        return sonarQubeService.getProjectMetrics(projectKey);
    }
}
