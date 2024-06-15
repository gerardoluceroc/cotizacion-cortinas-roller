package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.exceptions.EntityNotFoundException;
import com.civilo.roller.repositories.UserRepository;
import com.civilo.roller.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void saveUser(){
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, new RoleEntity(1L, "Cliente"));
        when(userRepository.save(user)).thenReturn(user);
        final UserEntity currentResponse = userService.createUser(user);
        assertEquals(user,currentResponse);
    }

    @Test
    void getUsers(){
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, new RoleEntity(1L, "Cliente"));
        List<UserEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(user);
        when((List<UserEntity>) userRepository.findAll()).thenReturn(expectedAnswer);
        final List<UserEntity> currentResponse = userService.getUsers();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    void getUserById(){
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, new RoleEntity(1L, "Cliente"));
        List<UserEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(user);
        when(userRepository.findById(Long.valueOf("9999"))).thenReturn(Optional.of(user));
        Optional<UserEntity> currentResponse = userService.getUserById(Long.valueOf("9999"));
        assertEquals(expectedAnswer.get(0).getUserID(), currentResponse.get().getUserID());
    }

    @Test
    void validateUser(){
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, new RoleEntity(1L, "Cliente"));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        user.setPassword("p");
        UserEntity currentResponse = userService.validateUser(user.getEmail(), user.getPassword());
        assertEquals(user, currentResponse);
    }

    @Test
    void validateUser2(){
        UserEntity user = null;
        when(userRepository.findByEmail("e")).thenReturn(user);
        UserEntity currentResponse = userService.validateUser("e", "p");
        assertEquals(null, currentResponse);
    }

    @Test
    public void getUserByEmail() {
        UserEntity user = new UserEntity();
        user.setEmail("example@example.com");
        when(userRepository.findByEmail("example@example.com")).thenReturn(user);
        UserEntity result = userService.getUserByEmail("example@example.com");
        Assertions.assertEquals(user, result);
    }

    @Test
    void validateEmailReturnsUserEntity() {
        String email = "test@example.com";
        UserEntity user = new UserEntity();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(user);
        Optional<UserEntity> result = userService.validateEmail(email);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void validateEmailReturnsEmptyOptional() {
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);
        Optional<UserEntity> result = userService.validateEmail(email);
        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateUser() {
        UserEntity user = new UserEntity();
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setPhoneNumber("123456789");
        user.setCommune("Santiago");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setAge(31);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserEntity updatedUser = userService.updateUser(1L, user);
        /*assertEquals("John", updatedUser.getName());
        assertEquals("Doe", updatedUser.getSurname());
        assertEquals("john.doe@example.com", updatedUser.getEmail());
        assertEquals("password", updatedUser.getPassword());
        assertEquals("123456789", updatedUser.getPhoneNumber());
        assertEquals("Santiago", updatedUser.getCommune());
        assertEquals(LocalDate.of(1990, 1, 1), updatedUser.getBirthDate());
        assertEquals(31, updatedUser.getAge());
        verify(userRepository, times(1)).save(updatedUser);
        */
    }

    @Test
    void testDeleteUserById() {
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);
        userService.deleteUserById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testExistsUserById() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new UserEntity()));
        boolean result = userService.existsUserById(userId);
        assertTrue(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void deleteUsers_shouldCallDeleteAllMethodInUserRepository() {
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, new RoleEntity(1L, "Cliente"));
        userService.deleteUsers();
        verify(userRepository, times(1)).deleteAll();
    }
}