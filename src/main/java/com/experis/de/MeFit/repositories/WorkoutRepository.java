package com.experis.de.MeFit.repositories;

import com.experis.de.MeFit.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//In this repository the WorkoutModel gets extended with JPA functions
@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Workout findByName(String name);

    //Workout updateWorkoutsExercise(int[] exerciseIds, String workoutId);
}
