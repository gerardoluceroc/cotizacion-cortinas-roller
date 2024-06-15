package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.civilo.roller.controllers.UserController;
import com.civilo.roller.repositories.UserRepository;
import com.civilo.roller.services.PermissionService;
import com.civilo.roller.services.RoleService;
import com.civilo.roller.services.SellerService;
import com.civilo.roller.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private SellerService sellerService;
    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PermissionService permissionService;

    @Mock
    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        List<UserEntity> expectedUsers = new ArrayList<>();
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        expectedUsers.add(new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime,  new RoleEntity(1L, "Cliente")));
        expectedUsers.add(new UserEntity(Long.valueOf("1111"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, new RoleEntity(1L, "Cliente")));
        when(userService.getUsers()).thenReturn(expectedUsers);
        List<UserEntity> actualUsers = userController.getUsers();
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void testSaveUser() {
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity expectedUser = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, new RoleEntity(1L, "Cliente"));;
        when(userService.createUser(Mockito.any(UserEntity.class))).thenReturn(expectedUser);
        UserEntity actualUser = userController.saveUser(new UserEntity());
        assertEquals(expectedUser, actualUser);
    }

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private MockMvc mockMvc;

    @Test
    public void testLogout() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession(false)).thenReturn(session);
        ResponseEntity<?> responseEntity = userController.logout(request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testLogoutWhenNoSession(){
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession(false)).thenReturn(null);
        ResponseEntity<?> responseEntity = userController.logout(request);
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testLoginSuccessful() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        Mockito.when(userService.validateUser(Mockito.anyString(), Mockito.anyString())).thenReturn(user);
        Mockito.when(request.getSession()).thenReturn(session);
        ResponseEntity<?> response = userController.login(user, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(session).setAttribute("user", user);
    }

    @Test
    public void testLoginFailed() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        Mockito.when(userService.validateUser(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        ResponseEntity<?> response = userController.login(user, request);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Mockito.verify(session, Mockito.never()).setAttribute(Mockito.anyString(), Mockito.any());
    }

    @Test
    void testGetUserByEmail() {
        String email = "email@example.com";
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", email, "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);

        when(userService.getUserByEmail(anyString())).thenReturn(user);

        UserEntity result = userController.getUserByEmail(email);
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testCreateUser() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        when(userService.validateEmail(user.getEmail())).thenReturn(Optional.empty());
        when(roleService.getRoleIdByAccountType(user.getRole().getAccountType())).thenReturn(Long.valueOf("1234"));
        ResponseEntity<?> response = userController.createUser(user);
        verify(userService, times(1)).createUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateUser_existingEmail() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        when(userService.validateEmail(any(String.class))).thenReturn(Optional.of(user));
        ResponseEntity<?> responseEntity = userController.createUser(user);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("El email ingresado ya existe", responseEntity.getBody());
    }

    @Test
    public void testUpdateUser() {
        long userId = 1L;
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        UserEntity updatedUser = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "newemail", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        when(userService.validateEmail(updatedUser.getEmail())).thenReturn(Optional.empty());
        ResponseEntity<?> response = userController.updateUser(userId, updatedUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).updateUser(userId, updatedUser);
    }

    @Test
    public void testUpdateUserUserNotFound() {
        long userId = 1L;
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity updatedUser = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "newemail", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        when(userService.getUserById(userId)).thenReturn(Optional.empty());
        ResponseEntity<?> response = userController.updateUser(userId, updatedUser);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("El usuario con el ID especificado no se encuentra registrado.", response.getBody());
        verify(userService, times(0)).updateUser(userId, updatedUser);
    }

    @Test
    public void testUpdateUserEmailConflict() {
        long userId = 1L;
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        UserEntity updatedUser = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "newemail", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        when(userService.validateEmail(updatedUser.getEmail())).thenReturn(Optional.of(user));
        ResponseEntity<?> response = userController.updateUser(userId, updatedUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void deleteUsersTest() {
        ResponseEntity<String> responseEntity = userController.deleteUsers();
        verify(userService).deleteUsers();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SE ELIMINARON LOS USUARIOS CORRECTAMENTE", responseEntity.getBody());
    }

    @Test
    public void testGetCurrentSession() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022, 9, 20), 20, startTime, endTime, role);
        when(userService.validateUser("Email", "Password")).thenReturn(user);
        UserEntity result = userController.getCurrentSession("Email", "Password");
        assertEquals(user, result);
    }

    @Test
    public void testLoginExecutive() {
        UserEntity userDTO = new UserEntity();
        userDTO.setEmail("email@example.com");
        userDTO.setPassword("password");
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Ejecutivo");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "email@example.com", "Password", "rut", "0 1234 5678", "Commune", null, 20, startTime, endTime, role);
        when(userService.validateUser(userDTO.getEmail(), userDTO.getPassword())).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        ResponseEntity<?> response = userController.loginExecutive(userDTO, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testLoginExecutiveNull() {
        UserEntity userDTO = new UserEntity();
        userDTO.setEmail("email");
        userDTO.setPassword("password");
        when(userService.validateUser(userDTO.getEmail(), userDTO.getPassword())).thenReturn(null);
        ResponseEntity<?> response = userController.loginExecutive(userDTO, request);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testLoginExecutiveInvalidRole() {
        RoleEntity role = new RoleEntity(Long.valueOf("9998"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        UserEntity userDTO = new UserEntity();
        userDTO.setEmail("email");
        userDTO.setPassword("password");
        when(userService.validateUser(userDTO.getEmail(), userDTO.getPassword())).thenReturn(user);
        ResponseEntity<?> response = userController.loginExecutive(userDTO, request);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testGetSession() {
        HttpSession session = mock(HttpSession.class);
        String sessionId = "session-id-123";
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, new RoleEntity(Long.valueOf("9999"), "Cliente"));
        when(session.getId()).thenReturn(sessionId);
        when(session.getAttribute("user")).thenReturn(user);
        String expected = "Session ID: " + sessionId + "\n" +
                "User email      : " + user.getEmail() + "\n" +
                "User full name  : " + user.getName() + " " + user.getSurname() + "\n" +
                "User role       : " + user.getRole().getAccountType() + "\n" +
                "User role ID    : " + user.getRole().getRoleID() + "\n" +
                "User permissions: null\n";
        String actual = userController.getSession(session);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteUserById_withNonExistingUser_shouldReturnNotFound() {
        Long nonExistingUserId = Long.valueOf("1234");
        when(userService.existsUserById(nonExistingUserId)).thenReturn(false);
        ResponseEntity<String> response = userController.deleteUserById(nonExistingUserId);
        assertEquals(ResponseEntity.notFound().build(), response);
        verify(userService, never()).deleteUserById(nonExistingUserId);
    }

    @Test
    public void testDeleteUserById_withExistingUser_shouldReturnOk() {
        Long existingUserId = Long.valueOf("5678");
        when(userService.existsUserById(existingUserId)).thenReturn(true);
        ResponseEntity<String> response = userController.deleteUserById(existingUserId);
        assertEquals(ResponseEntity.ok("USUARIO CON ID " + existingUserId + " ELIMINADO CORRECTAMENTE \n"), response);
        verify(userService, times(1)).deleteUserById(existingUserId);
    }

    @Test
    public void testCreateUserAsSeller() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Vendedor");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        String accountType = "Vendedor";
        Long IdRol = Long.valueOf("8888");
        SellerEntity seller = new SellerEntity(null, user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getRut(), user.getPhoneNumber(), user.getCommune(), user.getBirthDate(), user.getAge(), user.getStartTime(), user.getEndTime(), user.getRole(), null, false,  "banco", "cuenta", 1);
        Mockito.when(userService.validateEmail(user.getEmail())).thenReturn(Optional.empty());
        Mockito.when(roleService.getRoleIdByAccountType(accountType)).thenReturn(IdRol);
        Mockito.when(sellerService.saveSeller(Mockito.any(SellerEntity.class))).thenReturn(seller);
        ResponseEntity<?> responseEntity = userController.createUser(user);
        Mockito.verify(userService, Mockito.times(0)).createUser(user);
        Mockito.verify(sellerService, Mockito.times(1)).saveSeller(Mockito.any(SellerEntity.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void loginAdmin_WhenUserIsAdmin_ReturnOkResponse() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Administrador");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        when(userService.validateUser(anyString(), anyString())).thenReturn(user);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        ResponseEntity<?> response = userController.loginAdmin(user, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(session, times(1)).setAttribute(eq("user"), eq(user));
    }

    @Test
    void loginAdmin_WhenUserIsNotAdmin_ReturnUnauthorizedResponse() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Rol No Admin");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        when(userService.validateUser(anyString(), anyString())).thenReturn(user);
        ResponseEntity<?> response = userController.loginAdmin(user, request);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void loginAdmin_WhenUserIsNull_ReturnUnauthorizedResponse() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Rol No Admin");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        when(userService.validateUser(anyString(), anyString())).thenReturn(null);
        ResponseEntity<?> response = userController.loginAdmin(user, request);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}