package com.database.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User Entity for user table
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // This tells Hibernate to make a table out of this class
@ApiModel(value = "User Api model documentation", description = "Model")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "Unique id field of user object")
    private int id; //Unique id value for user

    @OneToMany(mappedBy = "user")
    @ApiModelProperty(value = "Unique name field of user object")
    private Set<ScoreWeek> scoreWeeks = new HashSet<>(); //Users

    @OneToMany(mappedBy = "user")
    @ApiModelProperty(value = "Unique name field of user object")
    private Set<ScoreMonth> scoreMonths = new HashSet<>();

    @ApiModelProperty(value = "Unique name field of user object")
    private String name;

    @ApiModelProperty(value = "Email address field of the user")
    private String email;

    @ApiModelProperty(value = "Password field of the user")
    private String password;

    @ApiModelProperty(value = "Indicate if user verified by mail")
    private boolean verified = false;

    private String role;

    public User(String name , String password , String email){

        this.name = name;
        this.password = password;
        this.email = email;
        this.role = "User";

    }

    public User(String name , String password){
        this.name = name;
        this.password = password;
    }





}