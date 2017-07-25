package com.tweetexport.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "Your feed is empty")
public class EmptyFeedExeption extends RuntimeException {
    public EmptyFeedExeption() {
    }
}
