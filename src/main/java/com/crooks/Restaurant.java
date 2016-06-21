/*
 * Copyright (c) 2016.
 */

package com.crooks;


import javax.persistence.*;

/**
 * Created by johncrooks on 6/21/16.
 */

@Entity         //required for it to be autogenerated with hibernate ORM
@Table(name = "restaurants")    //
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

    public Restaurant(String name, String location, int rating, String comment) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.comment = comment;
    }
}