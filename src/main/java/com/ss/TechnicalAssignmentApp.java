package com.ss;

import com.ss.services.MiscService;

public class TechnicalAssignmentApp {

    public static void main(String[] args){
        MiscService miscService = new MiscService();
        miscService.readAndProcessInput();
        String report = miscService.generateReportInJson();
        System.out.println("\n\nJson Report");
        System.out.println(report);
    }
}
