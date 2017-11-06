package com.viktor.yurlov.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;
    @JsonProperty("job_id")
    @Column(unique = true)
    private String jobId;
    @OneToOne(cascade = {CascadeType.ALL})
    private Task task;
    private String type;
    @JsonProperty("scheduled_at")
    private String scheduledAt;
    @JsonProperty("execute_times")
    private int executeTimes;
    @JsonProperty("start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date startTime;
    @JsonProperty("end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date endTime;
    @JsonProperty("time_zone")
    private String timeZone;
    @JsonProperty("callback_url")
    @Column(name = "callback_url")
    private String callbackUrl;
    @JsonProperty("next_run_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date nextRun;
    @JsonProperty("last_run_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date lastRun;
    @JsonProperty("last_run_result")
    @OneToOne(cascade = {CascadeType.ALL})
    private Result lastResult;

    public Job() {
    }

    public Job(String jobId, Task task, String type, String scheduledAt, int executeTimes, Date startTime, Date endTime, String timeZone, String callbackUrl) {
        this.jobId = jobId;
        this.task = task;
        this.type = type;
        this.scheduledAt = scheduledAt;
        this.executeTimes = executeTimes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeZone = timeZone;
        this.callbackUrl = callbackUrl;
    }

    public Job(String jobId, Task task, String type, String scheduledAt, int executeTimes, Date startTime, Date endTime, String timeZone, String callbackUrl, Date nextRun, Date lastRun, Result lastResult) {
        this.jobId = jobId;
        this.task = task;
        this.type = type;
        this.scheduledAt = scheduledAt;
        this.executeTimes = executeTimes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeZone = timeZone;
        this.callbackUrl = callbackUrl;
        this.nextRun = nextRun;
        this.lastRun = lastRun;
        this.lastResult = lastResult;
    }

    public Job(Task task, String type, String scheduledAt, int executeTimes, Date startTime, Date endTime, String timeZone, String callbackUrl) {
        this.task = task;
        this.type = type;
        this.scheduledAt = scheduledAt;
        this.executeTimes = executeTimes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeZone = timeZone;
        this.callbackUrl = callbackUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(String scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public int getExecuteTimes() {
        return executeTimes;
    }

    public void setExecuteTimes(int executeTimes) {
        this.executeTimes = executeTimes;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Date getNextRun() {
        return nextRun;
    }

    public void setNextRun(Date nextRun) {
        this.nextRun = nextRun;
    }

    public Date getLastRun() {
        return lastRun;
    }

    public void setLastRun(Date lastRun) {
        this.lastRun = lastRun;
    }

    public Result getLastResult() {
        return lastResult;
    }

    public void setLastResult(Result lastResult) {
        this.lastResult = lastResult;
    }


}
