package com.example.githubapi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Repository {
    private String repositoryName;
    private String ownerLogin;
    private List<Branch> branches;

    public Repository(String repositoryName, String ownerLogin, List<Branch> branches) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }
}
