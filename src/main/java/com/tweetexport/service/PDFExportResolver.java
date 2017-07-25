package com.tweetexport.service;

import com.itextpdf.text.DocumentException;

import java.io.ByteArrayInputStream;

public interface PDFExportResolver<T> {
    ByteArrayInputStream generatePdf(T inputData) throws DocumentException;
}
