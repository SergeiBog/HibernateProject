package ru.hibernateProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.hibernateProject.models.Book;
import ru.hibernateProject.models.People;
import ru.hibernateProject.services.BookService;
import ru.hibernateProject.services.PeopleService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model,@RequestParam(name = "page",required = false) Integer page,
                        @RequestParam(name = "books_per_page",required = false) Integer booksPerPage,
                        @RequestParam(name = "sort_by_year",required = false) boolean sortByYear){

        if(page==null||booksPerPage==null){
            model.addAttribute("books",bookService.findAll(sortByYear));
        }else {
            model.addAttribute("books", bookService.findWithPagination(page,booksPerPage,sortByYear));
        }
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("people") People people){
        model.addAttribute("books",bookService.findOne(id));
        People owner = bookService.getBookOwner(id);
        if(owner!=null){
            model.addAttribute("owner",owner);
        }else{
            model.addAttribute("person",peopleService.findAll());
        }

        return "books/show";
    }


    @GetMapping("/new")
    public String newBook(@ModelAttribute("books")Book book){
        return "books/new";
    }
    @PostMapping()
    public String createBook(@ModelAttribute("books") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id) {
        model.addAttribute("books", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("books") @Valid Book book,
                             BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookService.update(id, book);
        return "redirect:/books";
    }

    //Удаляем книгу
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("people") People people) {
        bookService.assign(people,id);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchPage(){
        return "books/search";
    }


    @PostMapping ("/search")
    public String searchBook(Model model,@RequestParam("query") String query){
        model.addAttribute("books",bookService.searchByTitle(query));
        return "books/search";
    }

}
