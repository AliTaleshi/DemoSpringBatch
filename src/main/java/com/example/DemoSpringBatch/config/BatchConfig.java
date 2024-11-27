package com.example.DemoSpringBatch.config;

import com.example.DemoSpringBatch.listener.SimpleJobExecutionListener;
import lombok.AllArgsConstructor;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
//    public static int n = 0;


    @Bean
    public Job demoJob(Step demoStep, SimpleJobExecutionListener listener) {
        return jobBuilderFactory.get("demoJob")
                .listener(listener)
                .start(demoStep)
                .build();
    }

    @Bean
    public Step demoStep(ItemReader<String> reader, ItemProcessor<String, String> processor, ItemWriter<String> writer, SimpleAsyncTaskExecutor simpleAsyncTaskExecutor) {
        return stepBuilderFactory.get("demoStep")
                .<String, String>chunk(5)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .taskExecutor(simpleAsyncTaskExecutor)
                .build();
    }

    @Bean
    public ItemReader<String> reader() {
        return new ItemReader<String>() {
            private int n = 0;

            @Override
            public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                if (n == 0) {
                    n++;
                    return "hello";
                }
                return null;
            }
        };
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

    @Bean
    public SimpleAsyncTaskExecutor customTaskExecutor() {
        return new SimpleAsyncTaskExecutor("spring_batch");
    }
}
