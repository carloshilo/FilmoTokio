package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Person;
import com.tokioschool.filmotokio.domain.enums.TypePerson;
import com.tokioschool.filmotokio.repository.PersonRepository;
import com.tokioschool.filmotokio.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepo;

    public PersonServiceImpl(PersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public Person addPerson(Person person) {
        log.info("Adding Person: {}", person);
        return personRepo.save(person);
    }

    @Override
    public List<Person> getPeopleByType(TypePerson type) {
        log.info("Fetching Person of PersonTypeEnum {}", type);
        return personRepo.findByTypeOrderByNameAsc(type);
    }
}
