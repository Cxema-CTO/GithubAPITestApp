package com.example.githubapi.controller;


import com.example.githubapi.entity.Repository;
import com.example.githubapi.service.GithubServiceImplementation;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
public class ApiController {

    private final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Autowired
    private GithubServiceImplementation gitHubService;

    @GetMapping("/{userName}/repos")
    public ResponseEntity<?> getUserRepos(@PathVariable String userName) {

        LOGGER.info("Get request for find the repositories of the user - " + userName);

        try {
            List<Repository> repositories = gitHubService.getUserRepositories(userName);
            return ResponseEntity.ok(repositories);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, "User not found"));
        }
    }

    @Getter
    @Setter
    static class ErrorResponse {
        private int status;
        private String message;

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}//end of class

