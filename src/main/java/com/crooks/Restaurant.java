/*
 * Copyright (c) 2016.
 */

package com.crooks;


import javax.persistence.*;

/**
 * Created by johncrooks on 6/21/16.
 */

@Entity         //required for it to be autogenerated with hibernate ORM
@Table(name = "restaurants")    //defining the column name when its Auto-genereated
public class Restaurant {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String location;
    @Column(nullable = false)
    int rating;
    @Column(nullable = false)
    String comment;

    @ManyToOne       //Signifies the many to one relationship btwn user and restaurant
    User user;

    public Restaurant(String name, String location, int rating, String comment, User user) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.comment = comment;
        this.user = user;
    }

    public Restaurant() {
    }
}
