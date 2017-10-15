package com.ss.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseStatistics {
    @JsonProperty("Status_code")
    private Integer statusCode;

    @JsonProperty("Number_of_responses")
    private Long count;

    public ResponseStatistics(Integer statusCode, Long count) {
        this.statusCode = statusCode;
        this.count = count;
    }
}
