package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Person;
import com.tokioschool.filmotokio.domain.enums.TypePerson;

import java.util.List;

public interface PersonService {

    Person addPerson(Person person);

    List<Person> getPeopleByType(TypePerson type);
}
