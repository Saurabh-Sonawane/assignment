package com.ss.client;

import com.ss.model.UrlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
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
            urlResponse.setUrl(url);
            ResponseEntity<String> response;
            try {
                response = restTemplate.getForEntity(url, String.class);
                if (response != null) {
                    urlResponse.setStatusCode(response.getStatusCodeValue());
                    urlResponse.setContentLength(response.getHeaders().getContentLength());
                    urlResponse.setDate(new Date(response.getHeaders().getDate()));
                }
            }catch (HttpClientErrorException e) {
                if (e.getResponseHeaders() != null) {
                    urlResponse.setStatusCode(e.getRawStatusCode());
                    urlResponse.setContentLength(e.getResponseHeaders().getContentLength());
                    urlResponse.setDate(new Date(e.getResponseHeaders().getDate()));
                }
            } catch (ResourceAccessException e) {
                if(e.getMessage().contains("Read timed out")) {
                    urlResponse.setError("Unable to get url content in 10 sec. Server may be slow or non responsive.");
                    System.out.print("Unable to get url content in 10 sec. Read time out after 10 sec : " + e.getMessage() + "\n");
                } else {
                    urlResponse.setError("Unexpected Error");
                    System.out.print("Error while fetching url content : "+e.getMessage()+"\n");
                }
            } catch (Exception e) {
                urlResponse.setError("Unexpected Error");
                System.out.print("Error while fetching url content : "+e.getMessage()+"\n");
            }
        }
        return urlResponse;
    }




}
