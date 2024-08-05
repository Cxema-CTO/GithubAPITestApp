package com.example.githubapi.service;


import com.example.githubapi.entity.Branch;
import com.example.githubapi.entity.Repository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.githubapi.constant.Constants.GITHUB_ACCESS_TOKEN;
import static com.example.githubapi.constant.Constants.GITHUB_API_URL;

@Service
public class GithubServiceImplementation implements GithubService {

    private final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Repository> getUserRepositories(String userName) {
        String url = UriComponentsBuilder.fromHttpUrl(GITHUB_API_URL + "/users/" + userName + "/repos")
                .queryParam("type", "owner")
                .queryParam("sort", "full_name")
                .toUriString();

        LOGGER.info("Url for GET request - " + url);

        Map<String, String> headers = new HashMap<>();

        //todo check this
        headers.put("Authorization", "token " + GITHUB_ACCESS_TOKEN);

        List<Repository> repositories = new ArrayList<>();
        List<Map<String, Object>> reposResponse = restTemplate.getForObject(url, List.class, headers);

        if (reposResponse != null) {
            for (Map<String, Object> repo : reposResponse) {
                if (!(Boolean) repo.get("fork")) {
                    String repoName = (String) repo.get("name");
                    String ownerLogin = ((Map<String, String>) repo.get("owner")).get("login");

                    String branchesUrl = ((String) repo.get("branches_url")).replace("{/branch}", "");
                    List<Map<String, Object>> branchesResponse = restTemplate.getForObject(branchesUrl, List.class);
                    List<Branch> branches = new ArrayList<>();

                    if (branchesResponse != null) {
                        for (Map<String, Object> branch : branchesResponse) {
                            String branchName = (String) branch.get("name");
                            String lastCommitSha = ((Map<String, String>) branch.get("commit")).get("sha");
                            branches.add(new Branch(branchName, lastCommitSha));
                        }
                    }
                    repositories.add(new Repository(repoName, ownerLogin, branches));
                }
            }
        }

        return repositories;
    }


}//end of class
