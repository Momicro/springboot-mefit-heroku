package com.experis.de.MeFit.repositories;

import com.experis.de.MeFit.models.UserRight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//In this repository the UserRightModel gets extended with JPA functions
@Repository
public interface UserRightRepository extends JpaRepository<UserRight, Long> {
}
