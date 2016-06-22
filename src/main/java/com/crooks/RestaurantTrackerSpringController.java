/*
 * Copyright (c) 2016.
 */

package com.crooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johncrooks on 6/21/16.
 */
@Controller
public class RestaurantTrackerSpringController {
    @Autowired
    UserRepository users;             //object created from the interface that we use to interact with the table
    @Autowired
    RestaurantRepository restaurants;


    @PostConstruct //Makes method run right when the controller is created aka before everything but spring booting up
    public void init(){
        if(users.count()==0){                       //if no user exists create a default one and add it to the DB.
            User user = new User("John", "pass");
            users.save(user);
        }
    }

    @RequestMapping(path="/", method= RequestMethod.GET)
    public String home(HttpSession session, Model model, Integer rating, String location, String search){
        String username = (String) session.getAttribute("username");
        if (username == null){
            return "login";
        }else{
            User user = users.findByName(username);

            Iterable<Restaurant> restaurantArrayList;
            if(search!=null){
                restaurantArrayList = restaurants.searchLocation(search);
            }
            else if (rating!=null && location !=null){
                restaurantArrayList = restaurants.findByRatingAndLocation(rating, location);
            }
            else if(location !=null){
                restaurantArrayList = restaurants.findByLocation(location);
            }
            else if(rating !=null) {
                restaurantArrayList= restaurants.findByRatingGreaterThanEqual(rating);
            }
            else{
                restaurantArrayList = restaurants.findByUser(user);
            }
            model.addAttribute("restaurants", restaurantArrayList);

            return "home";
        }
    } // End of home()

    @RequestMapping(path="/login", method=RequestMethod.POST)
    public String login(String username, String password, HttpSession session) throws Exception {
        User user = users.findByName(username);   // Setting User object equal to the "users" return from the table query
        if(user == null){
            user = new User(username, password);
            users.save(user);                   //This uses predefined method in hibernate from the interface we created (UserRepository)
        }
        else if(!user.password.equals(password)) {
            throw new Exception("------WRONG PASSWORD-----");
        }
        session.setAttribute("username", username);
        return "redirect:/";
    }

    @RequestMapping(path="/logout", method=RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path="/create-restaurant", method=RequestMethod.POST)
    public String create(String name, String location, int rating, String comment, HttpSession session){
        String username = (String) session.getAttribute("username");
        User user = users.findByName(username);
        Restaurant r = new Restaurant(name, location, rating, comment, user);
        restaurants.save(r);        //This uses predefined method in hibernate from the interface we created (UserRepository)
        return "redirect:/";

    }

    @RequestMapping(path="/delete-restaurant", method=RequestMethod.POST)
    public String delete(int id){
        restaurants.delete(id);
        return "redirect:/";
    }

}//End of Controller Class



