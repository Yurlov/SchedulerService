package com.viktor.yurlov.controller;

import com.viktor.yurlov.domain.*;
import com.viktor.yurlov.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
 *   @author Viktor Yurlov
 */
@RestController
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping(value = "/jobs")
    public JobBodyInfo<JobIdInfo> addNewJob(@Valid @RequestBody Request<Job> request) {
        Job job = new Job(
                request.getBody().getTask(),
                request.getBody().getType(),
                request.getBody().getScheduledAt(),
                request.getBody().getExecuteTimes(),
                request.getBody().getStartTime(),
                request.getBody().getEndTime(),
                request.getBody().getTimeZone(),
                request.getBody().getCallbackUrl());
        return new JobBodyInfo<>(new JobIdInfo(jobService.addJob(job)));

    }

    @GetMapping(value = "/jobs/{job_id}")
    public JobBodyInfo getById(@PathVariable("job_id") String id) {
        return new JobBodyInfo<>(jobService.getJobDetails(id));
    }

    @DeleteMapping(value = "/jobs/{job_id}")
    public Response deleteJob(@PathVariable("job_id") String id) {
        jobService.deleteJob(id);
        return new Response("200", "job deleted successfully");
    }

}
