package com.ss.services;

import com.ss.client.RestClient;
import com.ss.model.UrlResponse;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MiscService {

    private List<UrlResponse> urlList;
    private UrlValidator urlValidator = new UrlValidator();
    private RestClient restClient = new RestClient();

    public void readAndProcessInput() {
        Scanner scanner = new Scanner(System.in);
        String url = "";
        System.out.println("Please enter each URL on new line");
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
        } else {
            urlResponse.setError("Invalid Url");
        }
        urlList.add(urlResponse);
    }

    public List<UrlResponse> getUrlList() {
        return urlList;
    }
}
