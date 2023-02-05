package com.tokioschool.filmotokio.batch;

import com.tokioschool.filmotokio.domain.Film;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
@Slf4j
@AllArgsConstructor
public class MigrateJobConfig {

  public final @NonNull JobBuilderFactory jobBuilderFactory;
  public final @NonNull StepBuilderFactory stepBuilderFactory;
  public final @NonNull DataSource dataSource;

  @Bean
  @StepScope
  public JdbcCursorItemReader<Film> reader() {
    log.info("Preparing reader...");
    return new JdbcCursorItemReaderBuilder<Film>()
        .name("filmReader")
        .dataSource(dataSource)
        .sql("SELECT * FROM films")
        .rowMapper(new FilmMapper())
        .build();
  }

  @Bean
  @StepScope
  public FilmItemProcessor processor() {
    log.info("Preparing processor...");
    return new FilmItemProcessor();
  }

  @Bean
  @StepScope
  public FlatFileItemWriter<Film> writer() {
    log.info("Preparing writer...");
    return new FlatFileItemWriterBuilder<Film>()
        .name("filmWriter")
        .resource(new ClassPathResource("films.csv"))
        .lineAggregator(new FilmLineAggregator())
        .build();
  }

  @Bean

  public Job migrateFilmJob(MigrateFilmStartListener startListener, Step step) {
    return jobBuilderFactory.get("migrateFimJob")
        .incrementer(new RunIdIncrementer())
        .listener(startListener)
        .flow(step)
        .end()
        .build();
  }

  @Bean
  public Step step(FlatFileItemWriter<Film> writer, MigrateFilmWriteListener writeListener) {
    log.info("Preparing step...");
    return stepBuilderFactory.get("step")
        .<Film, Film>chunk(10)
        .reader(reader())
        .processor(processor())
        .writer(writer)
        .listener(writeListener)
        .build();
  }
}
