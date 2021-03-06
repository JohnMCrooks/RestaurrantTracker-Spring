/*
 * Copyright (c) 2016.
 */

package com.crooks;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;

/**
 * Created by johncrooks on 6/21/16.
 */
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
    public Iterable<Restaurant> findByRating(int rating);
    public Iterable<Restaurant> findByLocation(String location);
    public Iterable<Restaurant> findByRatingAndLocation(int rating, String location);
    public  Iterable<Restaurant> findByRatingGreaterThanEqual(int rating);
    public int countByLocation(String location);
    public Iterable<Restaurant> findByOrderByRatingDesc();

    public Iterable<Restaurant> findByUser(User user);

    @Query("SELECT r FROM Restaurant r WHERE r.location LIKE ?1%")  //use class name for 'Restaurant' specification. % sign is a wildcard
    public Iterable<Restaurant> searchLocation(String location);

}
