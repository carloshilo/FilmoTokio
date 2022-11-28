package com.tokioschool.filmotokio.dominio;

import lombok.*;

@AllArgsConstructor
public enum TypePersonEnum {
	SCREENWRITER("Screenwriter"), MUSICIAN("Musician"), PHOTOGRAPHER("Photographer"), ACTOR("Actor"),
	DIRECTOR("Director");
	
	@Getter
	private final String displayValue;

}