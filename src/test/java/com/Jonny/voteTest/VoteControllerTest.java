package com.Jonny.voteTest;

import com.Jonny.vote.entity.Menu;
import com.Jonny.vote.entity.Restaurant;
import com.Jonny.vote.entity.User;
import com.Jonny.vote.repository.MenuRepository;
import com.Jonny.vote.repository.RestaurantRepository;
import com.Jonny.vote.repository.UserRepository;
import com.Jonny.vote.service.VoteService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.verification.VerificationMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.Jonny.vote.entity.Role.ROLE_ADMIN;
import static com.Jonny.vote.entity.Role.ROLE_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

//@AutoConfigureWireMock(port = 0)
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VoteControllerTest {

	private static User user = new User(0, "user@yandex.ru", "User", "$2a$10$Sh0ZD2NFrzRRJJEKEWn8l.92ROEuzlVyzB9SV1AM8fdluPR0aC1ni", ROLE_USER);
	private static User admin = new User(1, "admin@gmail.com", "Admin", "$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju", ROLE_ADMIN);

	private static Restaurant restaurant = new Restaurant(0,"McDonalds");
	private static Restaurant restaurant1 = new Restaurant(1,"Шаляпин");
	private static Restaurant restaurant2 = new Restaurant(2,"Васаби");

	private static Menu menu = new Menu(0, restaurant, LocalDate.now());
	private static Menu menu1 = new Menu(1, restaurant1, LocalDate.now());

	private static List<User> userList = new ArrayList<>();
	private static List<Restaurant> listRestaurant = new ArrayList<>();
	private static List<Menu> listMenu = new ArrayList<>();

	static {
		userList.add(user);
		userList.add(admin);

		listRestaurant.add(restaurant);
		listRestaurant.add(restaurant1);
		listRestaurant.add(restaurant2);

		listMenu.add(menu);
		listMenu.add(menu1);
	}

	private static VoteService voteService;

	private static UserRepository userRepositoryMock;

	private static RestaurantRepository restaurantRepositoryMock;

	private static MenuRepository menuRepositoryMock;

	@BeforeAll
	public static void setUp(){
		voteService = new VoteService();

		userRepositoryMock = mock(UserRepository.class);
		restaurantRepositoryMock = mock(RestaurantRepository.class);
		menuRepositoryMock = mock(MenuRepository.class);

		voteService.setUserRepository(userRepositoryMock);
		voteService.setRestaurantRepository(restaurantRepositoryMock);
		voteService.setMenuRepository(menuRepositoryMock);

		listRestaurant.add(restaurant);
		listRestaurant.add(restaurant1);
		listRestaurant.add(restaurant2);
	}


	@Test
	public void voteServiceGetRestaurantsTest() {

		when(userRepositoryMock.save(user)).thenReturn(user);
		when(userRepositoryMock.findByEmail("user@yandex.ru")).thenReturn(user);
		when(userRepositoryMock.findById(0)).thenReturn(Optional.of(user));
		when(userRepositoryMock.findAll()).thenReturn(userList);

		when(restaurantRepositoryMock.findAll()).thenReturn(listRestaurant);
		when(restaurantRepositoryMock.findRestaurantByName("McDonalds")).thenReturn(restaurant);
		when(restaurantRepositoryMock.findRestaurantByName("Шаляпин")).thenReturn(restaurant1);
		when(restaurantRepositoryMock.findRestaurantByName("Васаби")).thenReturn(restaurant2);

		assertEquals(user, userRepositoryMock.save(user));
		assertEquals(user, userRepositoryMock.findByEmail("user@yandex.ru"));
		assertEquals(user, userRepositoryMock.findById(0).get());
		assertEquals(userList, userRepositoryMock.findAll());


		assertEquals(listRestaurant, voteService.getAllRestaurants());
		assertEquals(restaurant, voteService.getRestaurantByName("McDonalds"));
		assertEquals(restaurant1, voteService.getRestaurantByName("Шаляпин"));
		assertEquals(restaurant2, voteService.getRestaurantByName("Васаби"));
		assertEquals(user, userRepositoryMock.save(user));

		assertUser(user, userRepositoryMock.findById(0).get());
	}

	private void assertUser(User expected, User actual) {
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getPassword(), expected.getPassword());
		assertEquals(expected.getRoles(), expected.getRoles());
	}

	@Test
	public void voteServiceGetMenusTest()  {

		when(menuRepositoryMock.findByDate(LocalDate.now())).thenReturn(listMenu);

		assertEquals(listMenu, voteService.getItem("menus"));
	}



}
