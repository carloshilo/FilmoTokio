package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Person;
import com.tokioschool.filmotokio.domain.enums.TypePerson;
import com.tokioschool.filmotokio.repository.PersonRepository;
import com.tokioschool.filmotokio.service.PersonService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    private final @NonNull PersonRepository personRepository;

    public PersonServiceImpl(@NonNull PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person add(Person dto) {
        Person person = Person.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .type(dto.getType())
                .build();

        log.info("Adding Person: {}", person);
        return personRepository.save(person);
    }

    @Override
    public List<Person> getByType(TypePerson type) {
        log.info("Fetching Person of PersonTypeEnum {}", type);
        return personRepository.findByTypeOrderByNameAsc(type);
    }
}
