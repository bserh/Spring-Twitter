package com.tweetexport.exeptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Cannot generate pdf")
public class CanNotBuildPDFException extends RuntimeException {

    public CanNotBuildPDFException() {
    }
}
