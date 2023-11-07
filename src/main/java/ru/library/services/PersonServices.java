package ru.library.services;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.library.models.Book;
import ru.library.models.Person;
import ru.library.repositories.BookRepositories;
import ru.library.repositories.PersonRepositories;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonServices {
    private final PersonRepositories personRepositories;

    @Autowired
    public PersonServices(PersonRepositories personRepositories, BookRepositories bookRepositories) {
        this.personRepositories = personRepositories;
    }

    public List<Person> findAll() {
        return personRepositories.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = personRepositories.findById(id);
        return foundPerson.orElse(null);
    }

    public List<Book> getBooksByPersonId(int id){
        Optional<Person> person = personRepositories.findById(id);

        if(person.isPresent()){
            Hibernate.initialize(person.get().getBookList());
            return person.get().getBookList();
        }
        else
            return Collections.emptyList();
    }

    @Transactional
    public void save(Person person) {
        personRepositories.save(person);
    }

    @Transactional
    public void update(int id, Person updatePerson){
        updatePerson.setId(id);
        personRepositories.save(updatePerson);
    }

    @Transactional
    public void delete(int id){
        personRepositories.deleteById(id);
    }

}