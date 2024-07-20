package com.example.demo.Repository;


import com.example.demo.Model.JiraIssueNbJours;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Qualifier("dataEntityManagerFactory")
public interface JiraIssueNbJoursRepository extends JpaRepository<JiraIssueNbJours, Long> {
    Optional<JiraIssueNbJours> findByIssueKey(String issueKey);

}