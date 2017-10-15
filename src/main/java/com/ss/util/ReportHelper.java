package com.ss.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.model.ResponseStatistics;
import com.ss.model.UrlResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportHelper {

    ObjectMapper mapper = new ObjectMapper();

    public String generateUrlResponseJsonReport(List<UrlResponse> responseList) {
        String report=null;
        try {
            report = mapper.writeValueAsString(responseList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }

    public String generateResponseStatisticsJsonReport(Map<String, Long> responseMap) {
        String report=null;
        try {
            List<ResponseStatistics> list = new ArrayList<>();
            responseMap.entrySet().forEach(stringIntegerEntry -> {
                list.add(new ResponseStatistics(Integer.parseInt(stringIntegerEntry.getKey()), stringIntegerEntry.getValue()));
            });
            report = mapper.writeValueAsString(list);
            System.out.println(report);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }
}
