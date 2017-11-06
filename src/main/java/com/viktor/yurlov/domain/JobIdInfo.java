package com.viktor.yurlov.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobIdInfo {
    @JsonProperty("job_id")
    private String job_id;

    public JobIdInfo(String job_id) {
        this.job_id = job_id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }
}
