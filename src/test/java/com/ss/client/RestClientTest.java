package com.ss.client;

import com.ss.model.UrlResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class RestClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestClient restClient;

    @Test
    public void verifyFetchUrlContentReturnsValidResponse() throws Exception {

        //Given
        String url = "https://google.com";
        Mockito.when(restTemplate.getForEntity(Matchers.eq(url), Matchers.eq(String.class))).thenReturn(getDummyResponse(10000, 1507987295605L, HttpStatus.OK));

        //When
        UrlResponse response = restClient.fetchUrlContent(url);

        //Then
        assertEquals(url , response.getUrl());
        assertEquals(10000 , response.getContentLength());
        assertEquals(200 , response.getStatusCode());
        assertTrue(response.getDate() != null);
    }

    @Test
    public void verifyFetchUrlContentReturns400BadRequestAsResponse() throws Exception {

        //Given
        String url = "https://google.com";
        Mockito.when(restTemplate.getForEntity(Matchers.eq(url), Matchers.eq(String.class))).thenReturn(getDummyResponse(10000, 1507987295605L, HttpStatus.BAD_REQUEST));

        //When
        UrlResponse response = restClient.fetchUrlContent(url);

        //Then
        assertEquals(url , response.getUrl());
        assertEquals(10000 , response.getContentLength());
        assertEquals(400 , response.getStatusCode());
        assertTrue(response.getDate() != null);
    }

    @Test
    public void verifyFetchUrlContentReturns500InternalServerErrorAsResponse() throws Exception {

        //Given
        String url = "https://google.com";
        Mockito.when(restTemplate.getForEntity(Matchers.eq(url), Matchers.eq(String.class))).thenReturn(getDummyResponse(10000, 1507987295605L, HttpStatus.INTERNAL_SERVER_ERROR));

        //When
        UrlResponse response = restClient.fetchUrlContent(url);

        //Then
        assertEquals(url , response.getUrl());
        assertEquals(10000 , response.getContentLength());
        assertEquals(500 , response.getStatusCode());
        assertTrue(response.getDate() != null);
    }

    private ResponseEntity<String> getDummyResponse(long contentPlan, long date, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(contentPlan);
        headers.setDate(date);
        return new ResponseEntity<String>("OK", headers, status);
    }
}