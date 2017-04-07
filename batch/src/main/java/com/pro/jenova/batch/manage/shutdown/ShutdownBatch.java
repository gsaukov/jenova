package com.pro.jenova.batch.manage.shutdown;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class ShutdownBatch {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Job shutdownJob() throws Exception {
        return this.jobs.get("shutdownJob").start(shutdownStep()).build();
    }

    @Bean
    public Step shutdownStep() throws Exception {
        return this.steps.get("shutdownStep").tasklet(shutdownTasklet()).build();
    }

    @Bean
    public Tasklet shutdownTasklet() {
        return ((contribution, context) -> RepeatStatus.FINISHED);
    }

}
