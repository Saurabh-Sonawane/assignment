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
        assertEquals(10000 , response.getContentLength().longValue());
        assertEquals(200 , response.getStatusCode().longValue());
        assertTrue(response.getDate() != null);
    }

    @Test
    public void verifyFetchUrlContentReturnsException() throws Exception {

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

    private ResponseEntity<String> getDummyResponse(long contentPlan, long date, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(contentPlan);
        headers.setDate(date);
        return new ResponseEntity<String>("OK", headers, status);
    }
}