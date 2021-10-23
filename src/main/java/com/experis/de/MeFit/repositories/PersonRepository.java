package com.experis.de.MeFit.repositories;

import com.experis.de.MeFit.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//In this repository the PersonModel gets extended with JPA functions
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);
}
