package ru.hibernateProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hibernateProject.models.Book;
import ru.hibernateProject.models.People;
import ru.hibernateProject.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<People> findAll(){
        return peopleRepository.findAll();
    }

    public People findOne(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    public Optional<People> findOne(String name){
        return peopleRepository.findByFullName(name);
    }

    @Transactional
    public void save(People people){
        peopleRepository.save(people);
    }
    @Transactional
    public void update(int id,People people){
        people.setPeople_id(id);
        peopleRepository.save(people);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public List<Book> showBook(int id){
        List<Book> bookList = peopleRepository.findBookByPeople_id(id);
        for(Book book : bookList){
            long diffTime =  Math.abs(new Date().getTime() - book.getDateOfIssue().getTime());
            if(diffTime>864_000_000L) book.setOverdue(true);
        }
        return bookList;
    }
}
