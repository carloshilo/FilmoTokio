package com.tokioschool.filmotokiobatch;

import com.tokioschool.filmotokiobatch.config.JobBatchConfiguration;
import com.tokioschool.filmotokiobatch.listeners.MigrateFilmStartListener;
import com.tokioschool.filmotokiobatch.listeners.MigrateFilmWriteListener;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ComponentScan(basePackages = "com.tokioschool")
@SpringBatchTest
@SpringBootTest
@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JobBatchConfiguration.class, MigrateFilmStartListener.class, MigrateFilmWriteListener.class})
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

        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

}
