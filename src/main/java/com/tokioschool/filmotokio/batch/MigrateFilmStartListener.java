package com.tokioschool.filmotokio.batch;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class MigrateFilmStartListener extends JobExecutionListenerSupport {

  private final @NonNull JdbcTemplate jdbcTemplate;

  @Override
  public void beforeJob(JobExecution jobExecution) {
    log.info("Migrate job starting...");

    jdbcTemplate.query("SELECT COUNT (*) FROM films WHERE migrate = 0",
            (rs, rw) -> rs.getInt(1))
        .forEach(count -> log.info("{} Films to migrate", count));
  }
}
