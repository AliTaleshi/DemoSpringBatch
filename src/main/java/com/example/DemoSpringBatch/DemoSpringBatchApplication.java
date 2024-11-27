package com.example.DemoSpringBatch;

import com.example.DemoSpringBatch.listener.SimpleJobExecutionListener;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@AllArgsConstructor
public class DemoSpringBatchApplication {

    //	private Job demoJob;
    private JobBuilderFactory jobBuilderFactory;
    private SimpleJobExecutionListener simpleJobExecutionListener;
    private Step step;
    private JobLauncher jobLauncher;


    public static void main(String[] args) {
        SpringApplication.run(DemoSpringBatchApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Job demoJob = jobBuilderFactory.get("demoJob").incrementer(new RunIdIncrementer()).listener(simpleJobExecutionListener).start(step).build();
            int n = 100001;
            for (int i = 0; i < 10; i++) {
                jobLauncher.run(demoJob, new JobParametersBuilder().addString("key", String.valueOf(n)).toJobParameters());
                n++;
            }
        };
    }
}