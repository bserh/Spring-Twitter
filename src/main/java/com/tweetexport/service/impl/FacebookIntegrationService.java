package com.tweetexport.service.impl;

import com.tweetexport.service.SocialIntegrationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("facebook")
public class FacebookIntegrationService implements SocialIntegrationService<Post> {

    private final Facebook facebook;

    private final ConnectionRepository connectionRepository;

    public FacebookIntegrationService(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @Override
    public List<Post> exportFeed(int amount) {
        if (isConnected()) {
            return facebook.feedOperations().getFeed();
        }
        return null;
    }

    @Override
    public boolean isConnected() {
        return connectionRepository.findPrimaryConnection(Facebook.class) != null;
    }
}
