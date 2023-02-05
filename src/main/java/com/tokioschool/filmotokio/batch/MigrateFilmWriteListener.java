package com.tokioschool.filmotokio.batch;

import com.tokioschool.filmotokio.domain.Film;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class MigrateFilmWriteListener implements ItemWriteListener<Film> {

  private final @NonNull JdbcTemplate jdbcTemplate;

  @Override
  public void beforeWrite(List<? extends Film> list) {
  }

  @Override
  public void afterWrite(List<? extends Film> migratedFilms) {
    log.info("Updating migrated films in db");

    jdbcTemplate.batchUpdate(
        "UPDATE films SET migrate=?, date_migrate=? WHERE id=?",
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setBoolean(1, true);
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setLong(3, migratedFilms.get(i).getId());
          }

          @Override
          public int getBatchSize() {
            return migratedFilms.size();
          }
        });
  }

  @Override
  public void onWriteError(Exception e, List<? extends Film> list) {
    log.error("{}: error when writing Films :{}", e.getClass().getCanonicalName(), list, e);
  }
}
