package com.ss.services;

import com.ss.client.RestClient;
import com.ss.model.UrlResponse;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MiscService {

    private List<UrlResponse> urlList;
    private HashMap<String, Long> urlResponseStatistics = new HashMap<>();
    private UrlValidator urlValidator = new UrlValidator();
    private RestClient restClient = new RestClient();

    public void readAndProcessInput() {
        Scanner scanner = new Scanner(System.in);
        String url = "";
        System.out.println("\n\nPlease enter each URL on new line : \n");
        urlList = new ArrayList<>();
        while( scanner.hasNextLine() && (url = scanner.nextLine()) != null && url.length() > 0 ) {
            processUrl(url);
        }
    }

    private void processUrl(String url) {
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setUrl(url);
        if(urlValidator.isValid(url)) {
            urlResponse = restClient.fetchUrlContent(url);
            if(urlResponse.getError() == null )
            {
                processUrlStatistics(urlResponse);
            }
        } else {
            urlResponse.setError("Invalid Url");
        }
        urlList.add(urlResponse);
    }

    private void processUrlStatistics(UrlResponse urlResponse) {
        String key = urlResponse.getStatusCode().toString();
        Long value = urlResponseStatistics.get(key);
        if(value != null) {
            urlResponseStatistics.put(key, value + 1);
        } else {
            urlResponseStatistics.put(key, 1L);
        }
    }

    public List<UrlResponse> getUrlList() {
        return urlList;
    }

    public HashMap<String, Long> getUrlResponseStatistics() {
        return urlResponseStatistics;
    }

}
