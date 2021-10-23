package com.experis.de.MeFit.models;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "UserRight")
@Schema(description = "UserRight Model")
public  @Getter
        @Setter
        @RequiredArgsConstructor
class UserRight {

    //autogenerated ID which never has to be defined in future functions.
    @ApiModelProperty(notes = "ID of the user right", name = "id", value = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //Here the static components of the model
    @Column(name="name",
            nullable = false,
            columnDefinition = "TEXT")
    private String name;

    private enum Roles {USER, CONTRIBUTER, ADMIN};

}
