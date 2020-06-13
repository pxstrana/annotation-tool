package com.annotation.annotationtool.users;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.annotation.entities.User;
import com.annotation.repositories.UsersRepository;
import com.annotation.services.UsersService;
import com.annotation.services.exceptions.UserAlreadyExistException;
import com.annotation.services.exceptions.UserDoesNotExistsException;

@SpringBootTest
class UsersMockTests {

	@MockBean
	private UsersRepository userRepository;

	@Autowired
	private UsersService userService;

	/**
	 * Testing the Service part of Adding a user already registered.
	 */
	@Test
	void testAddRegistered() {
		// given
		User user = new User("luis", "annotator", "123");

		// when
		when(userRepository.findByUsername("luis")).thenReturn(userEntity(1L));

		// then
		try {
			userService.addUser(user);
			fail();
		} catch (UserAlreadyExistException e) {
			assertThat(e.getMessage(), any(String.class));
		}
	}

	@Test
	void testAddNew() throws UserAlreadyExistException {
		User user = new User("pedro", "annotator", "123");
		when(userRepository.findByUsername("pedro")).thenReturn(null);
		userService.addUser(user);
	}

	@Test
	void testDeleteRegistered() throws UserDoesNotExistsException {
		when(userRepository.existsById(1L)).thenReturn(true);
		userService.deleteUser(1L);

	}

	@Test
	void testDeleteNonExisting() {
		when(userRepository.existsById(1L)).thenReturn(false);
		try {
			userService.deleteUser(1L);
			fail();
		} catch (UserDoesNotExistsException e) {
		}
	}

	/**
	 * Test if the service updates the user. 
	 * Mockito return an Optional user which is always the same in this test simulating the db
	 * 
	 * @throws UserDoesNotExistsException If the user does not exists
	 */
	@Test
	void testUpdateExisting() throws UserDoesNotExistsException {
		User user = new User(1L, "luis", "annotator", "123");
		when(userRepository.findById(1L)).thenReturn(optionalUserEntity(1L));
		userService.updateUser(user);
	}

	@Test
	void testUpdateNonExisting() {
		User user = new User("luis", "annotator", "123");
		when(userRepository.findById(1L)).thenReturn(null);
		try {
			userService.updateUser(user);
			fail();
		} catch (UserDoesNotExistsException e) {
		}catch(NoSuchElementException e) {
			
		}
	}

	@Test
	void testListUsers() {
		ArrayList<User> lista = new ArrayList<>();
		lista.add(new User("luis"));
		lista.add(new User("pedro"));
		when(userRepository.findAll()).thenReturn(lista);
		List<User> lista2 = userService.getUsers();
		assertEquals(lista.size(), lista2.size());
		assertEquals(lista, lista2);
	}

	private User userEntity(Long id) {
		return new User(id, "luis", "annotator", "123");
	}

	private Optional<User> optionalUserEntity(Long id) {

		Optional<User> oUser = Optional.of(new User(id, "luis", "annotator", "123"));
		return oUser;
	}
}
