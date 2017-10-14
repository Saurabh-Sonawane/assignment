package com.ss.client;

import com.ss.model.UrlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

public class RestClient {

    private RestTemplate restTemplate;

    public RestClient() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(10000);
        this.restTemplate = new RestTemplate(requestFactory);
    }

    public UrlResponse fetchUrlContent(String url) {
        UrlResponse urlResponse = new UrlResponse();
        if(url != null && url.length() > 0) {

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response != null) {
                urlResponse.setUrl(url);
                urlResponse.setStatusCode(response.getStatusCodeValue());
                urlResponse.setContentLength(response.getHeaders().getContentLength());
                urlResponse.setDate(new Date(response.getHeaders().getDate()));
                System.out.println("Response : "+ response);
            }
        }
        return urlResponse;
    }




}
