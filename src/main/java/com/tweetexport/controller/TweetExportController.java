package com.tweetexport.controller;

import com.tweetexport.util.PdfGenerator;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.util.List;


@Controller
public class TweetExportController {


    private Twitter twitter;

    private ConnectionRepository connectionRepository;

    @Inject
    public TweetExportController(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }


    @RequestMapping(value = "/exportTweets", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> exportTweets(@RequestParam(value = "number", defaultValue = "5") String number) {
        HttpHeaders headers = new HttpHeaders();

        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return new ResponseEntity<>(null, headers, HttpStatus.FOUND);
        }

        headers.add("Content-Disposition", "inline; filename=tweetsExport.pdf");

        int tweetCount = Integer.parseInt(number);
        long userId = twitter.userOperations().getProfileId();

        List<Tweet> tweets = twitter.timelineOperations().getUserTimeline(userId, tweetCount);

        ByteArrayInputStream pdfStream = PdfGenerator.generatePdfStream(tweets);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}
