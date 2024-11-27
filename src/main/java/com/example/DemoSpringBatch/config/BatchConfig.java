package com.example.DemoSpringBatch.config;

import com.example.DemoSpringBatch.listener.SimpleJobExecutionListener;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job demoJob(Step demoStep, JobExecutionListener listener) {
        return jobBuilderFactory.get("demoJob")
                .listener(listener)
                .start(demoStep)
                .build();
    }

    @Bean
    public Step demoStep(ItemReader<String> reader, ItemProcessor<String, String> processor, ItemWriter<String> writer) {
        return stepBuilderFactory.get("demoStep")
                .<String, String>chunk(5)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<String> reader() {
        return () -> "Hello,World,Spring,Batch,Demo".split(",")[0];
    }

    @Bean
    public ItemProcessor<String, String> processor() {
        return item -> "Processed: " + item;
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> items.forEach(System.out::println);
    }

    @Bean
    public JobExecutionListener listener() {
        return new SimpleJobExecutionListener();
    }
}
