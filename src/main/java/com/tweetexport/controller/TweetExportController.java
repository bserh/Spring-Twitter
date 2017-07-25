package com.tweetexport.controller;

import com.itextpdf.text.DocumentException;
import com.tweetexport.exeptions.CanNotBuildPDFException;
import com.tweetexport.exeptions.EmptyFeedExeption;
import com.tweetexport.service.PDFExportResolver;
import com.tweetexport.service.SocialIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
public class TweetExportController {

    private final SocialIntegrationService twitterService;

    private final PDFExportResolver twitterPdfService;

    @Autowired
    public TweetExportController(@Qualifier("twitter") SocialIntegrationService twitterService, @Qualifier("twitterPdfResolver") PDFExportResolver twitterPdfService) {
        this.twitterService = twitterService;
        this.twitterPdfService = twitterPdfService;
    }


    @RequestMapping(value = "/exportTweets", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> exportTweets(@RequestParam(value = "number", defaultValue = "5") String number) {
        HttpHeaders headers = new HttpHeaders();


        int tweetCount = Integer.parseInt(number);
        List<Tweet> tweets = twitterService.exportFeed(tweetCount);

        if (tweets == null) {
            headers.add("Location", "/connect/twitter");
            return new ResponseEntity<>(null, headers, HttpStatus.FOUND);
        }

        if (tweets.size() == 0) {
            throw new EmptyFeedExeption();
        }

        ByteArrayInputStream pdfStream = null;
        try {
            pdfStream = twitterPdfService.generatePdf(tweets);

        } catch (DocumentException e) {
            throw new CanNotBuildPDFException();
        }

        headers.add("Content-Disposition", "inline; filename=tweetsExport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}
