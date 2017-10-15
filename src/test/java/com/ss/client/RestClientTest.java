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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class RestClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestClient restClient;

    @Test
    public void verifyFetchUrlContentIfReturnsValidResponse() throws Exception {

        //Given
        String url = "https://google.com";
        Mockito.when(restTemplate.getForEntity(Matchers.eq(url), Matchers.eq(String.class))).thenReturn(getDummyResponse(10000, 1507987295605L, HttpStatus.OK));

        //When
        UrlResponse response = restClient.fetchUrlContent(url);

        //Then
        assertEquals(url , response.getUrl());
        assertEquals(10000 , response.getContentLength().longValue());
        assertEquals(200 , response.getStatusCode().longValue());
        assertTrue(response.getDate() != null);
    }

    @Test
    public void verifyFetchUrlContentIfReturns404NotFound() throws Exception {

        //Given
        String url = "https://google.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(1234);
        headers.setDate(1507987295605L);
        Mockito.when(restTemplate.getForEntity(Matchers.eq(url), Matchers.eq(String.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Not found", headers, null, null));

        //When
        UrlResponse response = restClient.fetchUrlContent(url);

        //Then
        assertEquals(url , response.getUrl());
        assertEquals(1234 , response.getContentLength().longValue());
        assertEquals(404 , response.getStatusCode().longValue());
        assertTrue(response.getDate() != null);
    }

    @Test
    public void verifyFetchUrlContentIfReturnsTimeOutException() throws Exception {

        //Given
        String url = "https://google.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(1234);
        headers.setDate(1507987295605L);
        Mockito.when(restTemplate.getForEntity(Matchers.eq(url), Matchers.eq(String.class))).thenThrow(new ResourceAccessException("Read timed out"));

        //When
        UrlResponse response = restClient.fetchUrlContent(url);

        //Then
        assertEquals(url , response.getUrl());
        assertEquals("Unable to get url content in 10 sec. Server may be slow or non responsive." , response.getError());
    }

    @Test
    public void verifyFetchUrlContentIfReturnsException() throws Exception {

        //Given
        String url = "https://google.com";
        Mockito.when(restTemplate.getForEntity(Matchers.eq(url), Matchers.eq(String.class))).thenThrow(new RestClientException("Any exception"));

        //When
        UrlResponse response = restClient.fetchUrlContent(url);

        //Then
        assertEquals(url , response.getUrl());
        assertEquals("Unexpected Error" , response.getError());
    }

    private ResponseEntity<String> getDummyResponse(long contentPlan, long date, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(contentPlan);
        headers.setDate(date);
        return new ResponseEntity<String>("OK", headers, status);
    }
}