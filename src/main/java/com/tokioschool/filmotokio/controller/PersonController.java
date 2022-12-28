package com.tokioschool.filmotokio.controller;

import com.tokioschool.filmotokio.domain.Person;
import com.tokioschool.filmotokio.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/person")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @Secured({"ADMIN"})
    @GetMapping
    public String addPerson(@RequestParam(name = "person", required = false) String message,
                            ModelAndView modelAndView) {
        modelAndView.addObject("person", new Person());
        return "new-person";
    }
    @PostMapping
    public String addPerson(@ModelAttribute @Valid Person person,
                            BindingResult result,
                            ModelAndView modelAndView,
                            Principal principal)
    {
        if (result.hasErrors()) {
            log.error("Creation of Person {} failed because: {}", person, result.getAllErrors().toArray());
            return "new-person";
        }
        else {
            log.info("User {} added Person {}", principal.getName(), person);
            service.addPerson(person);
            return "redirect:/person/add?person=created";
        }
    }
}
