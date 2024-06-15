package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.RequestEntity;
import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.Entities.StatusEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.controllers.RequestController;
import com.civilo.roller.services.RequestService;
import com.civilo.roller.services.StatusService;
import com.civilo.roller.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class RequestControllerTest {
    @Mock
    private RequestService requestService;

    @Mock
    private UserService userService;

    @Mock
    private StatusService statusService;
    @InjectMocks
    private RequestController requestController;

    @Mock
    private HttpSession session;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRequests() {
        List<RequestEntity> requests = new ArrayList<>();
        RequestEntity request1 = new RequestEntity(Long.valueOf("1"), "Description 1", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason 1", 1, null, null, null, null, null);
        RequestEntity request2 = new RequestEntity(Long.valueOf("2"), "Description 2", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason 2", 1, null, null, null, null, null);
        requests.add(request1);
        requests.add(request2);
        when(requestService.getRequests()).thenReturn(requests);
        List<RequestEntity> result = requestController.getRequests();
        assertEquals(2, result.size());
        assertEquals("Description 1", result.get(0).getDescription());
        assertEquals("Reason 2", result.get(1).getReason());
    }

    @Test
    public void getRequestByIdTest() {
        RequestEntity request1 = new RequestEntity(Long.valueOf("1"), "Description 1", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason 1", 1, null, null, null, null, null);
        when(requestService.getRequestById(1L)).thenReturn(Optional.of(request1));
        ResponseEntity<RequestEntity> response = requestController.getRequestById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request1, response.getBody());
    }

    @Test
    public void getRequestByIdNotFoundTest() {
        when(requestService.getRequestById(1L)).thenReturn(Optional.empty());
        ResponseEntity<RequestEntity> response = requestController.getRequestById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateRequestWithValidUser() {
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        Mockito.when(userService.validateUser(user.getEmail(), user.getPassword())).thenReturn(user);
        Mockito.when(statusService.getStatus()).thenReturn(Arrays.asList(status));
        ResponseEntity<?> response = requestController.createRequest(requestEntity);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(requestService, Mockito.times(1)).saveRequest(requestEntity);
    }

    @Test
    public void testCreateRequestWithInvalidUser() {
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Vendedor");
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        Mockito.when(userService.validateUser(user.getEmail(), user.getPassword())).thenReturn(null);
        ResponseEntity<?> response = requestController.createRequest(requestEntity);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Mockito.verify(requestService, Mockito.never()).saveRequest(requestEntity);
    }

    @Test
    public void testCreateRequestWithNonClientUser() {
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Vendedor");
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        when(userService.validateUser(eq(user.getEmail()), eq(user.getPassword())))
                .thenReturn(user);
        ResponseEntity<?> response = requestController.createRequest(requestEntity);
        verify(userService).validateUser(eq(user.getEmail()), eq(user.getPassword()));
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testDeleteRequest() {
        doNothing().when(requestService).deleteRequest();
        ResponseEntity<String> response = requestController.deleteRequest();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SE ELIMINARON LAS SOLICITUDES CORRECTAMENTE", response.getBody());
    }

    @Test
    void testDeleteRequestById() {
        long id = 9999L;
        when(requestService.existsRequestById(id)).thenReturn(true);
        doNothing().when(requestService).deleteRequestById(id);
        ResponseEntity<String> response = requestController.deleteRequestById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SOLICITUD CON ID " + id + " ELIMINADA CORRECTAMENTE\n", response.getBody());
    }

    @Test
    void testGetRequestsBySellerId() {
        long sellerId = 9999L;
        List<RequestEntity> requests = new ArrayList<>();
        requests.add(new RequestEntity(Long.valueOf("9998"), "Description 2", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, null, null, null, null));
        requests.add(new RequestEntity(Long.valueOf("9997"), "Description 3", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, null, null, null, null));
        when(requestService.getRequestBySellerId(sellerId)).thenReturn(requests);
        List<RequestEntity> result = requestController.getRequestsBySellerId(sellerId);
        assertEquals(2, result.size());
    }

    @Test
    public void updateUser_requestNotFound_returnsNotFound() {
        Long id = Long.valueOf("9999");
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, null, null, null, null);
        when(requestService.getRequestById(id)).thenReturn(Optional.empty());
        ResponseEntity<?> response = requestController.updateRequest(id, requestEntity);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateUser_requestFound_returnsOk() {
        Long id = Long.valueOf("9999");
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, null, null, null, null);
        when(requestService.getRequestById(id)).thenReturn(Optional.of(requestEntity));
        ResponseEntity<?> response = requestController.updateRequest(id, requestEntity);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteRequestByIdNotFound() {
        Long id = Long.valueOf("9999");
        when(requestService.existsRequestById(id)).thenReturn(false);
        ResponseEntity<String> response = requestController.deleteRequestById(id);
        verify(requestService, times(1)).existsRequestById(id);
        verify(requestService, never()).deleteRequestById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testManualAssignment() {
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        Optional<RequestEntity> requestOptional = Optional.of(requestEntity);
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        when(statusService.getStatus()).thenReturn(Arrays.asList(new StatusEntity(), status));
        when(requestService.getRequestById(anyLong())).thenReturn(requestOptional);
        ResponseEntity<?> response = requestController.manualAssignment(9999, 9999);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testAutomaticAssignment() {
        ResponseEntity<?> response = requestController.automaticAssignment();
        verify(requestService, times(1)).automaticAssignment();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getRequestByUserIdTest() {
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        List<RequestEntity> requestList = new ArrayList<>();
        requestList.add(requestEntity);
        when(requestService.getRequestByUserId(anyLong())).thenReturn((ArrayList<RequestEntity>) requestList);
        ArrayList<RequestEntity> result = requestController.getRequestByUserId(9999L);
        verify(requestService, times(1)).getRequestByUserId(anyLong());
        assertEquals(1, result.size());
        assertEquals("Description", result.get(0).getDescription());
    }
}