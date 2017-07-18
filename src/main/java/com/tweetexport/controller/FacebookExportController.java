package com.tweetexport.controller;

import com.tweetexport.service.PDFExportResolver;
import com.tweetexport.service.SocialIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.Post;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
public class FacebookExportController {

    private final SocialIntegrationService facebookService;

    private final PDFExportResolver facebookPdfService;

    @Autowired
    public FacebookExportController(@Qualifier("facebook") SocialIntegrationService facebookService, @Qualifier("facebookPdfResolver") PDFExportResolver facebookPdfService) {
        this.facebookService = facebookService;
        this.facebookPdfService = facebookPdfService;
    }

    @RequestMapping(value = "/exportPosts", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> exportTweets(@RequestParam(value = "number", defaultValue = "5") String number) {
        HttpHeaders headers = new HttpHeaders();


        int postCount = Integer.parseInt(number);
        List<Post> posts = facebookService.exportFeed(postCount);
        if (posts == null) {
            headers.add("Location", "/connect/facebook");
            return new ResponseEntity<>(null, headers, HttpStatus.FOUND);
        }

        ByteArrayInputStream pdfStream = facebookPdfService.generatePdf(posts);

        headers.add("Content-Disposition", "inline; filename=postsExport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}
