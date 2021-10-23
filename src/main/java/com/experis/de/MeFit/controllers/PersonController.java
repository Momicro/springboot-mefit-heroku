package com.experis.de.MeFit.controllers;

import com.experis.de.MeFit.models.Exercise;
import com.experis.de.MeFit.models.Person;
import com.experis.de.MeFit.repositories.PersonRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    //get all Person
    @Operation(summary = "Get all persons without any filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Person",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "404", description = "No Person found",
                    content = @Content) })
    @GetMapping()
    public List<Person> getAllPerson(){
        return this.personRepository.findAll();
    }

    //get person by id
    @Operation(summary = "Get a specific person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "404", description = "No person found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable(value="id") long personId) {

        HttpStatus status;
        Person person = personRepository.findById(personId).get();

        status = (person != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(person, status);
    }

    //Create new person
    @Operation(summary = "Create Person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person person1 = personRepository.findByEmail(person.getEmail());

        if (person1 == null)
            return new ResponseEntity<>(personRepository.save(person), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Update Person
    @Operation(summary = "Update Person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable(value="id") long personId,
                                               @RequestBody Person personDetails) {

        Person person = personRepository.getById(personId);

        person.setEmail(personDetails.getEmail());
        person.setFirstName(personDetails.getFirstName());
        person.setLastName(personDetails.getLastName());
        //person.setPassword(personDetails.getPassword());

        return ResponseEntity.ok(this.personRepository.save(person));
    }

    //update password
    @Operation(summary = "Update password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "password updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PutMapping("/{id}/update_password")
    public ResponseEntity<Person> updatePassword(@PathVariable(value="id") long personId,
                                                 @RequestBody Person personDetails) {

        Person person = personRepository.getById(personId);

        person.setPassword(personDetails.getPassword());

        return ResponseEntity.ok(this.personRepository.save(person));
    }

    //delete person
    @Operation(summary = "Delete Person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) } )
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable(value = "id") long personId){

        Person person = personRepository.getById(personId);

        this.personRepository.delete(person);
    }

    //Upload picture
    @Operation(summary = "Upload Picture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Picture uploaded",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) } )
    @PostMapping("/{id}/picture")
    public ResponseEntity<Person> uploadPicture(@PathVariable(value="id") long personId,
                                                @RequestParam("file") MultipartFile multipartPicture) throws Exception {

        Person person = personRepository.getById(personId);

        try {
            person.setPicture(multipartPicture.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(this.personRepository.save(person));
    }

    //Download picture
    @Operation(summary = "Download Picture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Picture downloaded",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) } )
    @GetMapping(value = "/{id}/picture", produces = MediaType.IMAGE_JPEG_VALUE)
    Resource downloadPicture(@PathVariable(value="id") long personId) {

        byte[] picture = personRepository.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getPicture();

        return new ByteArrayResource(picture);
    }
}
