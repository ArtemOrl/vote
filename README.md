# vote spring-boot
Project Voting for the restaurant

Used technologies
* Spring-Boot 2;
* Spring-Security;
* Hibernate;
* SQL databases;
* JUnit and Mockito for tests. 

#### Users in DB

    User login: user@yandex.ru
    password: password
    
    Admin login: admin@gmail.com
    password: admin

#### GET ITEMS to vote after!


    How to get items?
    go to urls with basic authorization and use method GET
    http://localhost:8080/items?item=lunches returns JSON of launches for today;
    http://localhost:8080/items?item=restaurants returns JSON of restaurants;
    http://localhost:8080/items?item=menus returns JSON of menus for today;
    http://localhost:8080/items?item=votes returns JSON of votes for today;


#### See all users

    
    How to get users?
    go to url with admin basic authorization and use method GET
    http://localhost:8080/users returns JSON with all users
    

#### ADMIN should prepare DB of dishes to let users vote for restaurants

    How to add items like restaurant, its menu and lunch?
    go to url with admin basic authorization and use method POST with parameters
    http://localhost:8080/saveItem?item=restaurant&name={name of the restaurant} to add new restaurant to the database
    http://localhost:8080/saveItem?item=menu&name={name of the restaurant}&dateTime={2018-11-19} to add new menu for
    the restaurant to the database

    The lunch is the dish in the menu of the restaurant
    http://localhost:8080/saveItem?item=lunch&name={name of the lunch}&price={200}&id={menu_id} to add new menu for
    the restaurant to the database. To get menu_id use GET of http://localhost:8080/items?item=menus
     
#### USERS should vote for the restaurant (only one vote per day)
     
     How to vote for restaurant?
     First you need to see the lists of menus for today and to keep in mind the name of restaurant, for which you will vote
     When go to url with basic authorization and use method POST
     http://localhost:8080/voteForRestaurant?name={name of the restaurant}
     

