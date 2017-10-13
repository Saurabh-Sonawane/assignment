package com.ss.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScannerService {

    private List<String> urlList;

    public void readAndProcessInput() {
        Scanner scanner = new Scanner(System.in);
        String url = "";
        System.out.println("Please enter each URL on new line");
        urlList = new ArrayList<>();

        while( scanner.hasNextLine() && (url = scanner.nextLine()) != null && url.length() > 0 ) {
            urlList.add(url);
        }
        System.out.println(urlList);
    }

    public List<String> getUrlList() {
        return urlList;
    }
}
