package com.example.githubapi.service;

import com.example.githubapi.entity.Repository;

import java.util.List;

public interface GithubService {

    List<Repository> getUserRepositories(String userName);
}
