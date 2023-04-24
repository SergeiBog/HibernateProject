package ru.hibernateProject.models;

import com.sun.xml.bind.v2.model.core.ID;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "people")
public class People {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_id")
    private int people_id;
    @NotNull(message = "ФИО не может быть пустым")
    @Pattern(regexp = "[А-Я][а-я]* [А-Я][а-я]* [А-Я][а-я]*", message = "Проверьте форму записи \"Фамилия Имя Отчество\"")
    @Column(name = "fullName")
    private String fullName;

    @NotNull(message = "Год рождения не может быть пустым")
    @Min(value = 1900, message = "Год рождения долен быть не меньше 1900")
    @Max(value = 2023, message = "Вы не можете родиться в будущем")
    @Column(name = "yearOfBirth")
    private int yearOfBirth;


    @OneToMany(mappedBy = "visitor")
    private List<Book> bookList;
    public People(){

    }

    public People(int people_id,String fullName, int yearOfBirth) {
        this.people_id = people_id;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getPeople_id() {
        return people_id;
    }

    public void setPeople_id(int people_id) {
        this.people_id = people_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
