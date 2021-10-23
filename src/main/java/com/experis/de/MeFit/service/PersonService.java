package com.experis.de.MeFit.service;

import com.experis.de.MeFit.models.Person;

//Person service interface
public interface PersonService {

    //Function to create a new person with nested data inside.
    Person createPerson(Person person);

    //Function to delete the person with nested data inside.
    void deletePerson(Person person);
}
