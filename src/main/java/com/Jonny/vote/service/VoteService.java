package com.Jonny.vote.service;

import com.Jonny.vote.entity.*;
import com.Jonny.vote.repository.*;
import com.Jonny.vote.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("voteService")
public class VoteService {

    private static final LocalTime EXPIRED_TIME = LocalTime.parse("11:00");

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LunchRepository lunchRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;


    public List<Restaurant> getAllRestaurants(){
        return restaurantRepository.findAll();
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Restaurant getRestaurantByName(String name){return restaurantRepository.findRestaurantByName(name);}

    public Menu saveMenu(Menu menu){return menuRepository.save(menu);}

    public Lunch savelunch(Lunch lunch){return lunchRepository.save(lunch);}

    public Menu getMenuById(Integer id){ return menuRepository.getOne(id);}

    public Menu getMenuByRestaurantAndDate (Restaurant restaurant) {return menuRepository.findByRestaurantAndDate(restaurant, LocalDate.now());}

    public Optional<Vote> getForUserAndDate(int userId, LocalDate date) { return voteRepository.getForUserAndDate(userId, date); }

    public void delVote(Integer id){voteRepository.deleteById(id);}

    public List getItem (String parameter){
        LocalDate localDate = LocalDate.now();
        switch (parameter){
            case "lunches": return lunchRepository.findByDate(localDate);
            case "restaurants": return restaurantRepository.findAll();
            case "menus": return menuRepository.findByDate(localDate);
            case "votes": return voteRepository.getAllByDate(localDate);
            default: return new ArrayList();
        }
    }

    public boolean saveRestaurant(Restaurant restaurant){
        if (restaurant!=null){
            restaurantRepository.save(restaurant);
            return true;
        }
        return false;
    }

    @Transactional
    public Vote voteForRestaurant(String name){
        LocalDate today = LocalDate.now();
        Menu menu = getMenuByRestaurantAndDate(getRestaurantByName(name));
        Optional<Vote> vote = getForUserAndDate(LoggedUser.id(), today);
        if (vote.orElse(null)!=null && LocalTime.now().isBefore(EXPIRED_TIME)){
            delVote(getForUserAndDate(LoggedUser.id(), today).get().getId());
            return voteRepository.save(new Vote(userRepository.getOne(LoggedUser.id()), menu, today));
        } else if (vote.orElse(null)!=null && LocalTime.now().isAfter(EXPIRED_TIME)) {
            return getForUserAndDate(LoggedUser.id(), today).get();
        } else {
            return voteRepository.save(new Vote(userRepository.getOne(LoggedUser.id()), menu, today));
        }
    }
}
