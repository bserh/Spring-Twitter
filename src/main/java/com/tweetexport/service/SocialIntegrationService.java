package com.tweetexport.service;


import java.util.List;

public interface SocialIntegrationService<T> {
    List<T> exportFeed(int amount);

    boolean isConnected();
}
