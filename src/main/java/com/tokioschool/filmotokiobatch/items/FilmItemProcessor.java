package com.tokioschool.filmotokiobatch.items;

import com.tokioschool.filmotokio.domain.Film;
import org.springframework.batch.item.ItemProcessor;

public class FilmItemProcessor implements ItemProcessor<Film, Film> {
    @Override
    public Film process(Film item) throws Exception {
        if (!item.isMigrate()) {
            return null;
        }
        return item;
    }
}
