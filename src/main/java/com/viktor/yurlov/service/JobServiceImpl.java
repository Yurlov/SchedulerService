package com.viktor.yurlov.service;


import com.viktor.yurlov.adapter.HttpAdapter;
import com.viktor.yurlov.domain.Job;
import com.viktor.yurlov.domain.JobBodyInfo;
import com.viktor.yurlov.domain.Result;
import com.viktor.yurlov.exeption.EntityNotFoundException;
import com.viktor.yurlov.exeption.JobException;
import com.viktor.yurlov.repository.JobsRepository;
import com.viktor.yurlov.util.TimeUtil;
import org.quartz.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

@Service
public class JobServiceImpl implements JobService {

    private final Scheduler scheduler;
    private final JobsRepository jobsRepository;
    private final HttpAdapter httpAdapter;

    public JobServiceImpl(JobsRepository jobsRepository, Scheduler scheduler, HttpAdapter httpAdapter) {
        this.jobsRepository = jobsRepository;
        this.scheduler = scheduler;
        this.httpAdapter = httpAdapter;
    }

    private static void sendCallback(Job job) {
        if (job.getCallbackUrl() == null) throw new EntityNotFoundException("job not found");
        RestTemplate template = new RestTemplate();
        template.postForEntity(job.getCallbackUrl(), new JobBodyInfo<>(job), Object.class);

    }

    /*
     *@method  restart all jobs stored in our DB
      */
    @PostConstruct
    public void restartJobs() {
        jobsRepository.findAll().forEach(jobEntity -> startScheduleJob(jobEntity.getJobId()));
    }

    private void startScheduleJob(String id) {
        Job job = jobsRepository.findByJobId(id);
        JobDetail jobDetail = makeJob(job.getJobId());

        Trigger trigger = TriggerBuilder.newTrigger()
                .startAt(job.getStartTime())
                .withSchedule(CronScheduleBuilder
                        .cronSchedule(job.getScheduledAt())
                        .inTimeZone(TimeZone.getTimeZone(job.getTimeZone())))
                .endAt(job.getEndTime())
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            throw new JobException(e);
        }

    }

    @Override
    public String addJob(Job job) {
        String id = UUID.randomUUID().toString();
        job.setJobId(id);
        if (job.getStartTime() == null) {
            job.setStartTime(new Date());
        }
        if (job.getType() == null) {
            job.setType("http");
        }
        if (job.getTimeZone() == null) {
            job.setTimeZone("Asia/Singapore");
        }
        if (job.getEndTime() == null) {
            job.setEndTime(TimeUtil.futureTime(1000));
        }
        jobsRepository.save(job);

        startScheduleJob(job.getJobId());
        return job.getJobId();
    }

    private JobDetail makeJob(String id) {
        JobDataMap data = new JobDataMap();
        data.put("repository", jobsRepository);
        data.put("httpAdapter", httpAdapter);
        return JobBuilder
                .newJob(NotificationJob.class)
                .setJobData(data)
                .withIdentity(id)
                .build();
    }

    @Override
    public void deleteJob(String id) {
        Job job = jobsRepository.findByJobId(id);
        if (job == null) throw new EntityNotFoundException("job not found");

        try {
            scheduler.deleteJob(new JobKey(id));
        } catch (SchedulerException ignored) {
        }

        jobsRepository.deleteJob(job.getJobId());
        sendCallback(job);

    }

    @Override
    public Job getJobDetails(String id) {
        Job job = jobsRepository.findByJobId(id);
        if (job == null) throw new EntityNotFoundException("job not found");
        return job;
    }

    public class NotificationJob implements org.quartz.Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            JobDataMap data = jobExecutionContext.getJobDetail().getJobDataMap();
            String id = jobExecutionContext.getJobDetail().getKey().getName();
            JobsRepository jobsRepository = (JobsRepository) data.get("repository");
            HttpAdapter httpAdapter = (HttpAdapter) data.get("httpAdapter");

            Job job = jobsRepository.findByJobId(id);
            job.setLastRun(jobExecutionContext.getFireTime());
            job.setNextRun(jobExecutionContext.getNextFireTime());

            ResponseEntity response = httpAdapter.send(job.getTask());

            if (jobExecutionContext.getRefireCount() == job.getExecuteTimes()) {
                try {
                    scheduler.unscheduleJob(jobExecutionContext.getTrigger().getKey());
                } catch (SchedulerException e) {
                }
            }
            job.setLastResult(new Result(response.getStatusCodeValue(), response.getBody().toString()));

            jobsRepository.save(job);
            sendCallback(job);

        }
    }

}

