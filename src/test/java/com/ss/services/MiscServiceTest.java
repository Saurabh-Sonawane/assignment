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
        Mockito.when(restClient.fetchUrlContent(Matchers.contains("https://"))).thenReturn(getDummyUrlResponse());
        //When
        miscService.readAndProcessInput();

        //Then
        assertEquals(2, miscService.getUrlList().size());
        assertEquals("https://google.com", miscService.getUrlList().get(0).getUrl());
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

    private UrlResponse getDummyUrlResponse(){
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setUrl("https://google.com");
        urlResponse.setStatusCode(200);
        urlResponse.setContentLength(10000);
        urlResponse.setDate(new Date());
        return urlResponse;
    }

}