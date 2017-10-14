package com.ss.services;

import com.ss.client.RestClient;
import com.ss.model.UrlResponse;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class MiscServiceTest {

    @Mock
    private UrlValidator urlValidator = new UrlValidator();
    @Mock
    private RestClient restClient = new RestClient();

    @InjectMocks
    private MiscService miscService;

    @Test
    public void verifyReadAndProcessInput() throws Exception {
        //Given
        String input = "https://google.com\nhttps://yahoo.com";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Mockito.when(urlValidator.isValid(Matchers.contains("https://"))).thenReturn(true);
        Mockito.when(restClient.fetchUrlContent("https://google.com")).thenReturn(getDummyUrlResponse("https://google.com"));
        Mockito.when(restClient.fetchUrlContent("https://yahoo.com")).thenReturn(getDummyUrlResponse("https://yahoo.com"));
        //When
        miscService.readAndProcessInput();

        //Then
        assertEquals(2, miscService.getUrlList().size());
        assertEquals("https://google.com", miscService.getUrlList().get(0).getUrl());
        assertEquals("https://yahoo.com", miscService.getUrlList().get(1).getUrl());
    }

    @Test
    public void verifyErrorMessageGetsPopulatedForInvalidUrl() throws Exception {
        //Given
        String input = "abc://test.com";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Mockito.when(urlValidator.isValid(input)).thenReturn(false);
        //When
        miscService.readAndProcessInput();

        //Then
        assertEquals(1, miscService.getUrlList().size());
        assertEquals("abc://test.com", miscService.getUrlList().get(0).getUrl());
        assertEquals("Invalid Url", miscService.getUrlList().get(0).getError());
    }

    @Test
    public void verifyReportInJson() throws Exception {
        //Given
        String input = "https://google.com\nhttps://yahoo.com\nabc://test.com";
        String expectedReport = "[{\"Url\":\"https://google.com\",\"Status_code\":200,\"Content_length\":10000,\"Date\":\"Sat 14 Oct 2017 14:51:43 UTC\"},{\"Url\":\"https://yahoo.com\",\"Status_code\":200,\"Content_length\":10000,\"Date\":\"Sat 14 Oct 2017 14:51:43 UTC\"},{\"Url\":\"abc://test.com\",\"Status_code\":0,\"Content_length\":0,\"Error\":\"Invalid Url\"}]\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Mockito.when(urlValidator.isValid(Matchers.contains("https://"))).thenReturn(true);
        Mockito.when(urlValidator.isValid(Matchers.contains("abc://"))).thenReturn(false);
        Mockito.when(restClient.fetchUrlContent("https://google.com")).thenReturn(getDummyUrlResponse("https://google.com"));
        Mockito.when(restClient.fetchUrlContent("https://yahoo.com")).thenReturn(getDummyUrlResponse("https://yahoo.com"));
        miscService.readAndProcessInput();

        //When
        String jsonReport = miscService.generateReportInJson();

        //Then
        assertEquals(3, miscService.getUrlList().size());
        assertEquals("https://google.com", miscService.getUrlList().get(0).getUrl());
        assertEquals("https://yahoo.com", miscService.getUrlList().get(1).getUrl());
        assertEquals("abc://test.com", miscService.getUrlList().get(2).getUrl());
        assertTrue(jsonReport.contains("{\"Url\":\"https://google.com\",\"Status_code\":200,\"Content_length\":10000,\"Date\":\"Sat 14 Oct 2017"));
        assertTrue(jsonReport.contains("{\"Url\":\"https://yahoo.com\",\"Status_code\":200,\"Content_length\":10000,\"Date\":\"Sat 14 Oct 2017"));
        assertTrue(jsonReport.contains("{\"Url\":\"abc://test.com\",\"Error\":\"Invalid Url\"}"));
    }

    private UrlResponse getDummyUrlResponse(String url){
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setUrl(url);
        urlResponse.setStatusCode(200);
        urlResponse.setContentLength(new Long(10000));
        urlResponse.setDate(new Date());
        return urlResponse;
    }

}