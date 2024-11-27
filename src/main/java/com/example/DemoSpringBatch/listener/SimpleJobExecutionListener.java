package com.example.DemoSpringBatch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Job is starting...");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("Job has finished with status: " + jobExecution.getStatus());
    }
}
