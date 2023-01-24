package com.tokioschool.filmotokio.controller;

import com.tokioschool.filmotokio.domain.Person;
import com.tokioschool.filmotokio.service.PersonService;
import java.security.Principal;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {

  private final @NonNull PersonService service;

  @GetMapping("/add")
  public String addPerson(@RequestParam(name = "person", required = false) String message,
      Model model) {
    model.addAttribute("person", new Person());
    return "new-person";
  }

  @PostMapping("/add")
  public String addPerson(@ModelAttribute @Valid Person person,
      BindingResult result,
      Model model,
      Principal principal) {
    if (result.hasErrors()) {
      log.error("Creation of Person {} failed because: {}", person,
          result.getAllErrors().toArray());
      model.addAttribute("person", person);
      return "new-person";
    } else {
      log.info("User {} added Person {}", principal.getName(), person);
      service.add(person);
      return "redirect:/person/add?person=created";
    }
  }
}
