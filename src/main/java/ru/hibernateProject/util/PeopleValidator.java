package ru.hibernateProject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.hibernateProject.models.People;
import ru.hibernateProject.services.PeopleService;

@Component
public class PeopleValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PeopleValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return People.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        People people = (People) o;
        if (peopleService.findOne(people.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Такое Имя уже Зарегестрировано");
        }
    }
}
