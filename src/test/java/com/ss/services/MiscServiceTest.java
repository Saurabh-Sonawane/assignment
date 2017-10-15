package com.ss.services;

import com.ss.client.RestClient;
import com.ss.model.UrlResponse;
import com.ss.util.ReportHelperTest;
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

@RunWith(MockitoJUnitRunner.class)
public class MiscServiceTest {

    @Mock
    private UrlValidator urlValidator;
    @Mock
    private RestClient restClient;

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
    public void verifyReadAndProcessInputGeneratesUrlResponseStatistics() throws Exception {
        //Given
        String input = "https://google.com\nhttps://yahoo.com\nabc://test.com";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Mockito.when(urlValidator.isValid(Matchers.contains("https://"))).thenReturn(true);
        Mockito.when(restClient.fetchUrlContent("https://google.com")).thenReturn(getDummyUrlResponse("https://google.com"));
        Mockito.when(restClient.fetchUrlContent("https://yahoo.com")).thenReturn(getDummyUrlResponse("https://yahoo.com"));
        Mockito.when(restClient.fetchUrlContent("abc://test.com")).thenReturn(ReportHelperTest.getDummyUrlResponse("abc://test.com", true));

        //When
        miscService.readAndProcessInput();

        //Then
        assertEquals(1, miscService.getUrlResponseStatistics().size());
        assertEquals(2, Long.parseLong(miscService.getUrlResponseStatistics().get("200").toString()));
    }

    private UrlResponse getDummyUrlResponse(String url){
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setUrl(url);
        urlResponse.setStatusCode(200);
        urlResponse.setContentLength(new Long(10000));
        urlResponse.setDate(new Date(1507987295605L));
        return urlResponse;
    }

}