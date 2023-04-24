package ru.hibernateProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.hibernateProject.models.People;
import ru.hibernateProject.services.PeopleService;
import ru.hibernateProject.util.PeopleValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    private final PeopleValidator peopleValidator;

    @Autowired

    public PeopleController(PeopleService peopleService, PeopleValidator peopleValidator) {
        this.peopleService = peopleService;
        this.peopleValidator = peopleValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people",peopleService.findAll());
        return "people/index";
    }

    //Получаем одного человека по его id
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("people", peopleService.findOne(id));
        model.addAttribute("books",peopleService.showBook(id));
        return "people/show";
    }

    //Добавляем нового человека
    @GetMapping("/new")
    public String newPeople(@ModelAttribute("people") People people){
        return "people/new";
    }

    @PostMapping
    public String createPeople(@ModelAttribute("people") @Valid People people,
                               BindingResult bindingResult){
        peopleValidator.validate(people,bindingResult);
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        peopleService.save(people);
        return "redirect:/people";
    }

    //Редактируем Данные о человеке
    @GetMapping("/{id}/edit")
    public String editPeople(Model model,@PathVariable("id") int id){
        model.addAttribute("people", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePeople(@ModelAttribute("people") @Valid People people,
                               BindingResult bindingResult,@PathVariable("id") int id){
        peopleValidator.validate(people,bindingResult);
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        peopleService.update(id,people);
        return "redirect:/people";
    }

    //Удаляем Человека
    @DeleteMapping("/{id}")
    public String deletePeople(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}
