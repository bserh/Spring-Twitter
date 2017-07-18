package com.tweetexport.service;

import java.io.ByteArrayInputStream;

public interface PDFExportResolver<T> {
    ByteArrayInputStream generatePdf(T inputData);
}
