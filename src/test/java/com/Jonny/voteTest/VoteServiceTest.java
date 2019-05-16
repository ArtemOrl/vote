package com.Jonny.voteTest;

import com.Jonny.vote.entity.*;
import com.Jonny.vote.repository.*;
import com.Jonny.vote.service.VoteService;
import com.Jonny.vote.util.LoggedUser;
import com.google.auth.oauth2.AccessToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.Jonny.vote.entity.Role.ROLE_ADMIN;
import static com.Jonny.vote.entity.Role.ROLE_USER;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static junit.framework.Assert.assertEquals;

@WithMockUser
public class VoteServiceTest {

	private static User user = new User(0, "user@yandex.ru", "User", "$2a$10$Sh0ZD2NFrzRRJJEKEWn8l.92ROEuzlVyzB9SV1AM8fdluPR0aC1ni", ROLE_USER);
	private static User admin = new User(1, "admin@gmail.com", "Admin", "$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju", ROLE_ADMIN);

	private static Restaurant restaurant = new Restaurant(0,"McDonalds");
	private static Restaurant restaurant1 = new Restaurant(1,"Шаляпин");
	private static Restaurant restaurant2 = new Restaurant(2,"Васаби");

	private static Menu menu = new Menu(0, restaurant, LocalDate.now());
	private static Menu menu1 = new Menu(1, restaurant1, LocalDate.now());

	private static Lunch lunch = new Lunch("MacKak", 200, menu);
	private static Lunch lunch1 = new Lunch("MacPyatak", 200, menu);

	private static Vote vote = new Vote(user, menu, LocalDate.now());

	private static List<User> userList = new ArrayList<>();
	private static List<Restaurant> listRestaurant = new ArrayList<>();
	private static List<Menu> listMenu = new ArrayList<>();
	private static List<Lunch> listLunch = new ArrayList<>();
	private static List<Vote> listVote = new ArrayList<>();



	static {
		userList.add(user);
		userList.add(admin);

		listRestaurant.add(restaurant);
		listRestaurant.add(restaurant1);
		listRestaurant.add(restaurant2);

		listMenu.add(menu);
		listMenu.add(menu1);

		listLunch.add(lunch);
		listLunch.add(lunch1);

		listVote.add(vote);
	}

	@MockBean
	private static SecurityContext securityContext;

	@MockBean
	private static Authentication authentication;

	@MockBean
	private SecurityContextHolder securityContextHolder;

	private static VoteService voteService;

	private static UserRepository userRepositoryMock;

	private static RestaurantRepository restaurantRepositoryMock;

	private static MenuRepository menuRepositoryMock;

	private static LunchRepository lunchRepositoryMock;

	private static VoteRepository voteRepositoryMock;

	private static AccessToken mockAccessToken;

	@BeforeAll
	public static void setUp(){
		voteService = new VoteService();

		userRepositoryMock = mock(UserRepository.class);
		restaurantRepositoryMock = mock(RestaurantRepository.class);
		menuRepositoryMock = mock(MenuRepository.class);
		lunchRepositoryMock = mock(LunchRepository.class);
		voteRepositoryMock = mock(VoteRepository.class);

		mockAccessToken = mock(AccessToken.class);
		authentication = mock(Authentication.class);
		securityContext = mock(SecurityContext.class);

		voteService.setUserRepository(userRepositoryMock);
		voteService.setRestaurantRepository(restaurantRepositoryMock);
		voteService.setMenuRepository(menuRepositoryMock);
		voteService.setLunchRepository(lunchRepositoryMock);
		voteService.setVoteRepository(voteRepositoryMock);

	}


	@Test
	public void voteServiceGetUsersTest(){

		when(userRepositoryMock.save(user)).thenReturn(user);
		when(userRepositoryMock.findByEmail("user@yandex.ru")).thenReturn(user);
		when(userRepositoryMock.findById(0)).thenReturn(Optional.of(user));
		when(userRepositoryMock.findAll()).thenReturn(userList);

		assertEquals(user, userRepositoryMock.save(user));
		assertEquals(user, userRepositoryMock.findByEmail("user@yandex.ru"));
		assertEquals(user, userRepositoryMock.findById(0).get());
		assertEquals(userList, userRepositoryMock.findAll());

		assertUser(user, userRepositoryMock.findById(0).get());
	}

	@Test
	public void voteServiceGetRestaurantsTest() {

		when(restaurantRepositoryMock.findAll()).thenReturn(listRestaurant);
		when(restaurantRepositoryMock.findRestaurantByName("McDonalds")).thenReturn(restaurant);
		when(restaurantRepositoryMock.findRestaurantByName("Шаляпин")).thenReturn(restaurant1);
		when(restaurantRepositoryMock.findRestaurantByName("Васаби")).thenReturn(restaurant2);

		assertEquals(listRestaurant, voteService.getAllRestaurants());
		assertEquals(restaurant, voteService.getRestaurantByName("McDonalds"));
		assertEquals(restaurant1, voteService.getRestaurantByName("Шаляпин"));
		assertEquals(restaurant2, voteService.getRestaurantByName("Васаби"));

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

	@Test
	public void voteServiceGetLunchesTest(){

		when(lunchRepositoryMock.findByDate(LocalDate.now())).thenReturn(listLunch);

		assertEquals(listLunch, voteService.getItem("lunches"));

	}

	@Test
	public void voteServiceGetVotesTest(){

		when(voteRepositoryMock.getAllByDate(LocalDate.now())).thenReturn(listVote);

		assertEquals(listVote, voteService.getItem("votes"));

	}

	@Test
	public void saveRestaurantTest(){
		when(restaurantRepositoryMock.save(restaurant)).thenReturn(restaurant);
		assertEquals(true, voteService.saveRestaurant(restaurant));
	}
	@Test
	public void saveMenuTest(){
		when(menuRepositoryMock.save(menu)).thenReturn(menu);
		assertEquals(menu, voteService.saveMenu(menu));
	}
	@Test
	public void saveLaunchTest(){
		when(lunchRepositoryMock.save(lunch)).thenReturn(lunch);
		assertEquals(lunch, voteService.savelunch(lunch));
	}

	public static class MockSecurityContext implements SecurityContext {

		private static final long serialVersionUID = -1386535243513362694L;

		private Authentication authentication;

		public MockSecurityContext(Authentication authentication) {
			this.authentication = authentication;
		}

		@Override
		public Authentication getAuthentication() {
			return this.authentication;
		}

		@Override
		public void setAuthentication(Authentication authentication) {
			this.authentication = authentication;
		}
	}

	@Test
	@WithMockUser(username = "User", password = "password", roles = "ROLES_USER")
	public void voteForRestaurantTest(){

		// Mock user autorisation
		securityContext.setAuthentication(authentication);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(new LoggedUser(user));

		//Mock of method inside of voteService.voteForRestaurant(String name);
		Optional<Vote> optionalVote = Optional.of(vote);
		when(voteRepositoryMock.getForUserAndDate(0, LocalDate.now())).thenReturn(optionalVote);


		when(voteRepositoryMock.save(vote)).thenReturn(vote);
		assertEquals(vote, voteService.voteForRestaurant("McDonalds"));
	}
}
