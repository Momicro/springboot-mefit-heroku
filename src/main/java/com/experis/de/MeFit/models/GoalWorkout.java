package com.experis.de.MeFit.models;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
//import java.util.Date;

@Entity
@Table(name = "GoalWorkout")
@Schema(description = "GoalWorkout Model")
public  @Getter
        @Setter
        @RequiredArgsConstructor
class GoalWorkout {

    //autogenerated ID which never has to be defined in future functions.
    @ApiModelProperty(notes = "ID of the goal workout", name = "id", value = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //autogenerated ID which never has to be defined in future functions.
    @Column(name="completed",
            nullable = false,
            columnDefinition = "TEXT")
    private boolean completed;
}