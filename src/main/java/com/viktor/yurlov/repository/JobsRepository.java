package com.viktor.yurlov.repository;


import com.viktor.yurlov.domain.Job;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface JobsRepository extends CrudRepository<Job, Integer> {

    //    @Query("select j from Job j where j.jobId= :jobId")
    Job findByJobId(@Param("jobId") String jobId);

    @Query("delete from Job j where j.jobId = :jobId")
    void deleteJob(@Param("jobId") String jobId);
}
