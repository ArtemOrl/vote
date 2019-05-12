package com.Jonny.vote.Controller;

import com.Jonny.vote.entity.*;
import com.Jonny.vote.service.VoteService;
import com.Jonny.vote.util.DateTimeUtil;
import com.Jonny.vote.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

/*
Users in DataBase:

User login: user@yandex.ru
password: password

Admin login: admin@gmail.com
password: admin

 */
@Controller
public class VoteController {



    @Autowired
    private VoteService voteService;


    /*
    How to get items?
    go to url with basic authorization and use method GET
    http://localhost:8080/items?item=lunches returns JSON of launches for today;
    http://localhost:8080/items?item=restaurants returns JSON of restaurants;
    http://localhost:8080/items?item=menus returns JSON of menus for today;
    http://localhost:8080/items?item=votes returns JSON of votes for today;
    */

    @GetMapping("/items")
    public ResponseEntity<?> getItems(@RequestParam(value = "item",
            required = false) String param) {
        return new ResponseEntity<>(voteService.getItem(param), HttpStatus.OK);
    }

    /*
    How to get users?
    go to url with admin basic authorization and use method GET
    http://localhost:8080/users returns JSON with all users
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> users() {
        return ResponseEntity.ok(voteService.getAllUsers());
    }

    /*
    How to add items like restaurant, its menu and lunch?
    go to url with admin basic authorization and use method POST with parameters
    http://localhost:8080/saveItem?item=restaurant&name={name of the restaurant} to add new restaurant to the database
    http://localhost:8080/saveItem?item=menu&name={name of the restaurant}&dateTime={2018-11-19} to add new menu for
    the restaurant to the database

    The lunch is the dish in the menu of the restaurant
    http://localhost:8080/saveItem?item=lunch&name={name of the lunch}&price={200}&id={menu_id} to add new menu for
    the restaurant to the database. To get menu_id use GET of http://localhost:8080/items?item=menus
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/saveItem")
    public ResponseEntity saveItem(@RequestParam String item,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "dateTime", required = false) String dateTime,
                                   @RequestParam(value = "id", required = false) Integer id,
                                   @RequestParam(value = "price", required = false) Integer price){

        switch (item){
            case "restaurant":
                voteService.saveRestaurant(new Restaurant(name));
                return ResponseEntity.ok(Collections.singletonMap("status", "Restaurant with name " + name
                        + " has been saved sucessfuly"));
            case "menu":
                Menu menu = voteService.saveMenu(new Menu(voteService.getRestaurantByName(name), DateTimeUtil.parseLocalDate(dateTime)));
                return ResponseEntity.ok(Collections.singletonMap("status", menu + " has been saved sucessfuly"));
            case "lunch":
                Lunch lunch = voteService.savelunch(new Lunch(name, price, voteService.getMenuById(id)));
                return ResponseEntity.ok(Collections.singletonMap("status", lunch + " has been saved sucessfuly"));
            default:
                return new ResponseEntity<>("Nothing doesn't work! Fuck you!", HttpStatus.OK);
        }
    }

    /*
    How to vote for restaurant?
    First you need to see the lists of menus for today and to keep in mind the name of restorant, for which you will vote
    When go to url with basic authorization and use method POST
    http://localhost:8080/voteForRestaurant?name={name of the restaurant}
     */

    @PostMapping("/voteForRestaurant")
    public ResponseEntity vote(@RequestParam String name){

        Vote vote = voteService.voteForRestaurant(name);
        return ResponseEntity.ok(Collections.singletonMap("status", vote + " has been saved sucessfuly"));

    }

    @PostMapping("/delVote")
    public ResponseEntity vote(@RequestParam Integer id){

        voteService.delVote(id);
        return ResponseEntity.ok(Collections.singletonMap("status",  "Vote has with id " + id + " been deleted sucessfuly"));

    }

}
