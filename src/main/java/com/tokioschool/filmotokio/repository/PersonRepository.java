package com.tokioschool.filmotokio.repository;

import com.tokioschool.filmotokio.domain.Person;
import com.tokioschool.filmotokio.domain.enums.TypePerson;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  List<Person> findByTypeOrderByNameAsc(TypePerson type);
}

