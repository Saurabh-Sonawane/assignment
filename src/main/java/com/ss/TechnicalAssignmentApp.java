package com.ss;

import com.ss.services.ScannerService;

public class TechnicalAssignmentApp {

    public static void main(String[] args){
        ScannerService scannerService = new ScannerService();
        scannerService.readAndProcessInput();
    }
}
