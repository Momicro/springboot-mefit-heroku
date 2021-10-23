package com.experis.de.MeFit.service;

import com.experis.de.MeFit.models.UserRight;

//UserRight service interface
public interface UserRightService {

    //Function to create a new user right with nested data inside.
    UserRight createUserRight(UserRight userRight);

    //Function to delete the user right with nested data inside.
    void deleteUserRight(UserRight userRight);
}
