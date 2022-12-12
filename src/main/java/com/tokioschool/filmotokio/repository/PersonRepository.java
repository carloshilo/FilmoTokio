package com.tokioschool.filmotokio.repository;

import com.tokioschool.filmotokio.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}

