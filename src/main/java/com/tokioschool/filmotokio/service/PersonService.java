package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Person;
import com.tokioschool.filmotokio.domain.enums.TypePerson;

import java.util.List;

public interface PersonService {

    Person add(Person person);

    List<Person> getByType(TypePerson type);
}
