package com.tokioschool.filmotokio.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3799241297762460904L;

    private long id;

    private String title;

    private int year;

    private String poster;

    private float avgScore;
}
