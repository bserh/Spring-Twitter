package com.tweetexport.controller;

import com.tweetexport.service.SocialIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/")
public class HomeController {

    private final SocialIntegrationService facebookService;

    private final SocialIntegrationService twitterService;

    @Autowired
    public HomeController(@Qualifier("facebook") SocialIntegrationService facebookService, @Qualifier("twitter") SocialIntegrationService twitterService) {
        this.facebookService = facebookService;
        this.twitterService = twitterService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("isFacebookConnected", facebookService.isConnected());
        model.addAttribute("isTwitterConnected", twitterService.isConnected());
        return "home";
    }

}
