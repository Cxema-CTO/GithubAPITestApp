package com.example.githubapi.controller;

import com.example.githubapi.entity.Repository;
import com.example.githubapi.service.GithubServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import static com.example.githubapi.constant.Constants.HTML.*;
import static com.example.githubapi.constant.Constants.USER_NAME;


@Controller
public class MainController {

    @Autowired
    private GithubServiceImplementation gitHubService;

    private final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (!Objects.equals(session.getAttribute(USER_NAME), null)) {
            model.addAttribute(USER_NAME, session.getAttribute(USER_NAME));
        }
        return INDEX_HTML;
    }

    @PostMapping("/")
    public String result(HttpServletRequest request, Model model, @RequestParam String userName) {
        HttpSession session = request.getSession();
        userName = userName.trim();
        session.setAttribute(USER_NAME, userName);
        model.addAttribute(USER_NAME, userName);
        LOGGER.info("Find the repositories of the user - " + userName);

        try {
            List<Repository> repositoryList = gitHubService.getUserRepositories(userName);
            model.addAttribute("repositories", repositoryList);
            System.out.println(repositoryList);
        } catch (Exception e) {
            model.addAttribute("error", "Can't find repositories for user \"" + userName + "\"");
            model.addAttribute("info", "maybe rate limit exceeded, try again later");
            LOGGER.error("Can't find repositories for user \"" + userName + "\"");
            LOGGER.error("Receive answer from github - " + e);
            return ERROR_HTML;
        }

        return RESULT_HTML;
    }


}//end of class
