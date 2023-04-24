package ru.hibernateProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hibernateProject.models.Book;
import ru.hibernateProject.models.People;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<People,Integer> {

    Optional<People> findByFullName(String name);

    @Modifying
    @Query("select b from Book b where b.visitor.people_id=?1")
    List<Book> findBookByPeople_id(int id);
}
