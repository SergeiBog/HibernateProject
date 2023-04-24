package ru.hibernateProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hibernateProject.models.Book;
import ru.hibernateProject.models.People;
import ru.hibernateProject.repositories.BookRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(boolean sortByYear){
        if(sortByYear) {
            return bookRepository.findAll(Sort.by("publishYear"));
        }else {
            return bookRepository.findAll();
        }
    }

    public List<Book> findWithPagination(int page,int size,boolean sortByYear){
        if(sortByYear){
            return bookRepository.findAll(PageRequest.of(page,size,Sort.by("publishYear"))).getContent();
        }else {
            return bookRepository.findAll(PageRequest.of(page,size)).getContent();
        }
    }

    public List<Book> searchByTitle(String str){
        return bookRepository.findByTitleStartingWith(str);
    }

    public Book findOne(int id){
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id,Book book){
        book.setBook_id(id);
        bookRepository.save(book);

    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    @Transactional
    public void assign(People people,int id){
        Book book = bookRepository.findById(id).orElse(null);
        assert book != null;
        book.setDateOfIssue(new Date());
        book.setVisitor(people);

    }

    @Transactional
    public void release(int id){
        Book book = bookRepository.findById(id).orElse(null);
        assert book != null;
        book.setDateOfIssue(null);
        book.setVisitor(null);

    }

    @Transactional
    public People getBookOwner(int id){
       return bookRepository.findById(id).map(Book::getVisitor).orElse(null);
    }

    public boolean isOverdue(Date date){
        Long current = System.currentTimeMillis();
        return current - date.getTime() < (864_000_000L);
    }
}
