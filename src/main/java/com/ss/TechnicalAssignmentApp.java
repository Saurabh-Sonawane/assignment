package com.ss;

import com.ss.services.MiscService;
import com.ss.util.ReportHelper;

public class TechnicalAssignmentApp {

    public static void main(String[] args){
        MiscService miscService = new MiscService();
        ReportHelper reportHelper = new ReportHelper();

        miscService.readAndProcessInput();

        String report = reportHelper.generateUrlResponseJsonReport(miscService.getUrlList());
        System.out.println("\n\nURL Response Json Report : \n");
        System.out.println(report);

        report = reportHelper.generateResponseStatisticsJsonReport(miscService.getUrlResponseStatistics());
        System.out.println("\n\nURL Response Statistic Json Report : \n");
        System.out.println(report);

    }
}
