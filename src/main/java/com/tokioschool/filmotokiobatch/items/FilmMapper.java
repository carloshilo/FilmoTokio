package com.tokioschool.filmotokiobatch.items;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class FilmMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = (new BeanPropertyRowMapper<>(Film.class).mapRow(rs, rowNum));

        if (Objects.isNull(film)) {
            return null;
        }

        film.setUser(User.builder()
                .id(rs.getLong("user_id"))
                .build());

        return film;
    }
}
