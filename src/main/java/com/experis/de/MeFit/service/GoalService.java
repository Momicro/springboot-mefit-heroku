package com.experis.de.MeFit.service;

import com.experis.de.MeFit.models.Goal;

//Goal service interface
public interface GoalService {

    Goal createGoal(Goal goal);

    //Function to delete the goal with nested data inside.
    void deleteGoal(Goal goal);

}
