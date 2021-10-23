package com.experis.de.MeFit.controllers;

import com.experis.de.MeFit.models.Goal;
import com.experis.de.MeFit.models.Person;
import com.experis.de.MeFit.repositories.GoalRepository;
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
//import java.util.Date;

@RestController
@RequestMapping("/api/v1/goals")
public class GoalController {

    @Autowired
    GoalRepository goalRepository;

    //get all goals
    @Operation(summary = "Get all goals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of goals",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Goal.class)) }),
            @ApiResponse(responseCode = "404", description = "No goals found",
                    content = @Content) })
    @GetMapping
    public List<Goal> getAllGoals(){
        return this.goalRepository.findAll();
    }

    //get goal by id
    @Operation(summary = "Get goals by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Selected goal",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Goal.class)) }),
            @ApiResponse(responseCode = "404", description = "Goal not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable(value="id") long goalId) {
        Goal goal = goalRepository.findById(goalId).get();
        return ResponseEntity.ok().body(goal);
    }

    //create goal
    @Operation(summary = "Create a new goal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Goal created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Goal.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
        Goal goal1 = goalRepository.findByStartDate(goal.getStartDate());

        if (goal1 == null)
            return new ResponseEntity<>(goalRepository.save(goal), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //ATM not working due to problems with createGoal and this one

    //update goal
    @Operation(summary = "Update Goal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Goal updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Goal.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable(value="id") long goalId,
                                           @RequestBody Goal goalDetails) {

        Goal goal = goalRepository.getById(goalId);
        goal.setStartDate(goalDetails.getStartDate());
        goal.setEndDate(goalDetails.getEndDate());
        goal.setArchived(goalDetails.isArchived());

        return ResponseEntity.ok(this.goalRepository.save(goal));
    }

    //delete goal
    @Operation(summary = "Delete goal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Goal deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Goal.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public void deleteGoal(@PathVariable(value = "id") long goalId){

        Goal goal = goalRepository.getById(goalId);

        this.goalRepository.delete(goal);
    }

}
