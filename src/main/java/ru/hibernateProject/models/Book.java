package ru.hibernateProject.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int book_id;

    @NotNull(message = "название книги не может быть пустым")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Имя Автора не может быть пустым")
    @Pattern(regexp = "[А-Я][а-я]* [А-Я][а-я]*", message = "Автор должен быть записан в форме \"Фамилия Имя\"")
    @Column(name = "author")
    private String author;

    @NotNull(message = "Год не может быть пустым")
    @Column(name = "publishyear")
    private int publishYear;

    @Column(name="date_of_issue")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfIssue;

    @Transient
    private boolean overdue;

    @ManyToOne
    @JoinColumn(name = "people_id",referencedColumnName = "people_id")
    private People visitor;
    public Book(){

    }

    public Book(int book_id,String title, String author, int publishYear) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public People getVisitor() {
        return visitor;
    }

    public void setVisitor(People visitor) {
        this.visitor = visitor;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date date) {
        this.dateOfIssue = date;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }
}
