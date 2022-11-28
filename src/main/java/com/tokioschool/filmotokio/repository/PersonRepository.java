package com.tokioschool.filmotokio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.filmotokio.dominio.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}

