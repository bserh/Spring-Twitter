package com.tweetexport.service.impl;

import com.tweetexport.service.SocialIntegrationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("twitter")
public class TwitterIntegrationService implements SocialIntegrationService<Tweet> {
    private final Twitter twitter;

    private final ConnectionRepository connectionRepository;

    public TwitterIntegrationService(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }

    @Override
    public List<Tweet> exportFeed(int amount) {

        if (isConnected()) {
            long userId = twitter.userOperations().getProfileId();
            return twitter.timelineOperations().getUserTimeline(userId, amount);
        }

        return null;

    }

    @Override
    public boolean isConnected() {
        return connectionRepository.findPrimaryConnection(Twitter.class) != null;
    }
}
