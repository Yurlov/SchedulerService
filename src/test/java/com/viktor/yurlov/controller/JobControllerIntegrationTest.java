package com.viktor.yurlov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktor.yurlov.domain.*;
import com.viktor.yurlov.service.JobService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(JobController.class)
public class JobControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    private SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private Map<String, String> map = new HashMap<>();

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateJobSuccess() throws Exception {
        map.put("Content-Type", "application/json");
        map.put("Accept", "application/json");
        map.put("Authorization", "Bearer XXXXXX");
        Job job = new Job(
                new Task("POST", "http://example.com/", map,
                        "data_here"), "http", "*/10 * * ? * 1-5", 10,
                sm.parse("2017-02-01 12:00:00"), sm.parse("2018-02-01 12:00:00"),
                "Asia/Singapore", "https://result.com/callback");

        Request<Job> request = new Request<>();
        request.setBody(job);
        JobBodyInfo<JobIdInfo> response = new JobBodyInfo<>();
        response.setBody(new JobIdInfo("1"));


        when(jobService.addJob(any(Job.class))).thenReturn("1");

        this.mockMvc.perform(post("/jobs")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(response)));

        verify((jobService), times(1)).addJob(any(Job.class));
    }

    @Test
    public void testDeleteJobByIdSuccess() throws Exception {

        mockMvc.perform(
                delete("/jobs/{job_id}", "1").
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().
                        json(asJsonString(new Response("200", "job deleted successfully"))));

    }

    @Test
    public void testGetJobDetailsById() throws Exception {
        map.put("Content-Type", "application/json");
        map.put("Accept", "application/json");
        map.put("Authorization", "Bearer XXXXXX");
        Job job = new Job("12345678",
                new Task("POST", "http://example.com/", map, "data_here"),
                "http", "*/10 * * ? * 1-5", 10,
                sm.parse("2017-02-01 12:00:00"), sm.parse("2018-02-01 12:00:00"),
                "Asia/Singapore", "https://result.com/callback");
        JobBodyInfo<Job> response = new JobBodyInfo<>();
        response.setBody(job);
        when(jobService.getJobDetails("12345678")).thenReturn(job);

        mockMvc.perform(get("/jobs/{job_id}", "12345678"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(response)));

        verify(jobService, times(1)).getJobDetails("12345678");
    }


}
