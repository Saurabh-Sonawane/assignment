package com.ss.util;

import com.ss.model.UrlResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class ReportHelperTest {


    private ReportHelper reportHelper = new ReportHelper();

    @Test
    public void verifyResponseJsonReport() throws Exception {
        //Given
        List<UrlResponse> list = new ArrayList<>();
        list.add(getDummyUrlResponse("https://google.com", false));
        list.add(getDummyUrlResponse("https://yahoo.com", false));
        list.add(getDummyUrlResponse("abc://test.com", true));
        //When
        String jsonReport = reportHelper.generateUrlResponseJsonReport(list);
        //Then
        assertTrue(jsonReport.contains("{\"Url\":\"https://google.com\",\"Status_code\":200,\"Content_length\":10000,\"Date\":\"Sat 14 Oct 2017"));
        assertTrue(jsonReport.contains("{\"Url\":\"https://yahoo.com\",\"Status_code\":200,\"Content_length\":10000,\"Date\":\"Sat 14 Oct 2017"));
        assertTrue(jsonReport.contains("{\"Url\":\"abc://test.com\",\"Error\":\"Invalid Url\"}"));
    }

    @Test
    public void verifyResponseStatisticsJsonReport() throws Exception {
        //Given
        HashMap<String, Long> map = new HashMap<>();
        map.put("200",3L);
        map.put("400",5L);
        //When
        String jsonReport = reportHelper.generateResponseStatisticsJsonReport(map);
        //Then
        assertTrue(jsonReport.contains("[{\"Status_code\":200,\"Number_of_responses\":3},{\"Status_code\":400,\"Number_of_responses\":5}]"));
    }

    public static UrlResponse getDummyUrlResponse(String url,boolean error){
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setUrl(url);
        if(error) {
            urlResponse.setError("Invalid Url");
        } else {
            urlResponse.setStatusCode(200);
            urlResponse.setContentLength(new Long(10000));
            urlResponse.setDate(new Date(1507987295605L));
        }
        return urlResponse;
    }

}