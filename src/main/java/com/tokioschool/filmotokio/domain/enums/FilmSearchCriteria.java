package com.tokioschool.filmotokio.domain.enums;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum FilmSearchCriteria {
    TITLE("Title"),
    YEAR("Year"),
    MAX_DURATION("Max Length"),
    AVG_SCORE("Average Score"),
    ACTOR("Actor"),
    DIRECTOR("Director"),
    SCREENWRITER("Screenwriter"),
    CINEMATOGRAPHER("Cinematographer"),
    COMPOSER("Composer");


    private final String displayValue;

    FilmSearchCriteria(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public static FilmSearchCriteria fromString(String value) {
        for (FilmSearchCriteria criteria : FilmSearchCriteria.values()) {
            if (criteria.getDisplayValue().equalsIgnoreCase(value)) {
                return criteria;
            }
            else {
                try {
                    if (FilmSearchCriteria.valueOf(value) == criteria) {
                        return criteria;
                    }
                }
                catch (Exception e) {
                    log.error("Error when fetching FilmSearchCriteriaEnum", e);
                }
            }
        }
        throw new IllegalArgumentException("No matching search criteria found");
    }

}
