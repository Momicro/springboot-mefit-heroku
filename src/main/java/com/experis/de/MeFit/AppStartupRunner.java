package com.experis.de.MeFit;

import com.experis.de.MeFit.models.*;
import com.experis.de.MeFit.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class AppStartupRunner implements ApplicationRunner {

    private final ExerciseRepository exerciseRepository;
    private final GoalRepository goalRepository;
    private final ProfileRepository profileRepository;
    private final ProgramRepository programRepository;
    private final PersonRepository personRepository;
    private final WorkoutRepository workoutRepository;

    @Autowired
    public AppStartupRunner(ExerciseRepository exerciseRepository, GoalRepository goalRepository,
                            ProfileRepository profileRepository, ProgramRepository programRepository,
                            PersonRepository personRepository, WorkoutRepository workoutRepository) {
        this.exerciseRepository = exerciseRepository;
        this.goalRepository = goalRepository;
        this.profileRepository = profileRepository;
        this.programRepository = programRepository;
        this.personRepository = personRepository;
        this.workoutRepository = workoutRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

        /*
        this.exerciseRepository.deleteAll();
        this.goalRepository.deleteAll();
        this.profileRepository.deleteAll();
        this.programRepository.deleteAll();
        this.userRepository.deleteAll();
        this.workoutRepository.deleteAll();
        */

        var exercisesList = new HashSet<Exercise>();

        Exercise exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("sit ups");
        exercise.setDescription("lay down on your back and push upper body");
        exercisesList.add(exercise);

        exercise = new Exercise();
        exercise.setId(2L);
        exercise.setName("pushups");
        exercise.setDescription("push you upper body up while lying on your belly");
        exercisesList.add(exercise);

        exerciseRepository.saveAll(exercisesList);

    }
}