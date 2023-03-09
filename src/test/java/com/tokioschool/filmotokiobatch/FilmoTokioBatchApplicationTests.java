package com.tokioschool.filmotokiobatch;

import com.tokioschool.filmotokiobatch.config.JobBatchConfiguration;
import org.junit.After;
import org.junit.Test;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;

@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {JobBatchConfiguration.class})
public class FilmoTokioBatchApplicationTests {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @After
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    public void executorTest()
            throws Exception {
        var jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        var jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        var actualJobInstance = jobExecution.getJobInstance();
        var actualJobExitStatus = jobExecution.getExitStatus();
    }

}
