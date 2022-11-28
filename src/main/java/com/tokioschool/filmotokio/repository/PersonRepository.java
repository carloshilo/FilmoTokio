package com.tokioschool.filmotokio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.filmotokio.dominio.dto.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

}

