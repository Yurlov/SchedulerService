package com.viktor.yurlov.service;

import com.viktor.yurlov.domain.Job;


public interface JobService {
    String addJob(Job job);

    void deleteJob(String id);

    Job getJobDetails(String id);
}
