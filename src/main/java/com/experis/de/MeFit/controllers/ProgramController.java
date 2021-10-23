package com.experis.de.MeFit.controllers;

import com.experis.de.MeFit.models.Program;
import com.experis.de.MeFit.repositories.ProgramRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/programs")
public class ProgramController {

    @Autowired
    ProgramRepository programRepository;

    //get all exercises
    @Operation(summary = "Get all programs without any filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of programs",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Program.class)) }),
            @ApiResponse(responseCode = "404", description = "No programs found",
                    content = @Content) })
    @GetMapping()
    public List<Program> getAllPrograms(){
        return this.programRepository.findAll();
    }

    //get exercise by id
    @Operation(summary = "Get a specific program")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Program.class)) }),
            @ApiResponse(responseCode = "404", description = "No program found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Program> getProgramById(@PathVariable(value="id") long programId) {

        HttpStatus status;
        Program program = programRepository.findById(programId).get();

        status = (program != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(program, status);
    }

    //save program
    @Operation(summary = "Save program")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Program.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Program> createProgram(@RequestBody Program program) {
        Program program1 = programRepository.findByName(program.getName());

        if (program1 == null)
            return new ResponseEntity<Program>(programRepository.save(program), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //update program
    @Operation(summary = "Update program")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Program.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<Program> updateProgram(@PathVariable(value="id") long programId,
                                                   @RequestBody Program programDetails) {

        Program program = programRepository.getById(programId);

        program.setName(programDetails.getName());
        program.setCategory(programDetails.getCategory());
        program.setWorkouts(programDetails.getWorkouts());

        return ResponseEntity.ok(this.programRepository.save(program));
    }

    //delete exercise
    @Operation(summary = "Delete program")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Program.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public void deleteProgram(@PathVariable(value = "id") long programId){

        Program program = programRepository.getById(programId);

        this.programRepository.delete(program);
    }

    //Delete all programs
    @Operation(summary = "Delete all programs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All programs deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Program.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @DeleteMapping
    public void deleteAllPrograms(){
        this.programRepository.deleteAll();
    }

}
