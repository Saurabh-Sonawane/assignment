package com.ss.services;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;


public class ScannerServiceTest {
    private ScannerService scannerService;

    @Before
    public void setUp() throws Exception {
        scannerService = new ScannerService();
    }

    @Test
    public void verifyReadAndProcessInput() throws Exception {
        //Given
        String input = "https://google.com\nhttps://yahoo.com";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //When
        scannerService.readAndProcessInput();

        //Then
        assertEquals(2, scannerService.getUrlList().size());
        assertEquals("https://google.com", scannerService.getUrlList().get(0).getUrl());
    }

    @Test
    public void verifyErrorMessageGetsPopulatedForInvalidUrl() throws Exception {
        //Given
        String input = "abc://test.com";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //When
        scannerService.readAndProcessInput();

        //Then
        assertEquals(1, scannerService.getUrlList().size());
        assertEquals("abc://test.com", scannerService.getUrlList().get(0).getUrl());
        assertEquals("Invalid Url", scannerService.getUrlList().get(0).getError());
    }

}