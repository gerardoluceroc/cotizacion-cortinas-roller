package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.StatusEntity;
import com.civilo.roller.controllers.StatusController;
import com.civilo.roller.services.StatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class StatusControllerTest {
    @Mock
    private StatusService statusService;
    @InjectMocks
    private StatusController statusController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStatus() {
        List<StatusEntity> expectedStatus = new ArrayList<>();
        expectedStatus.add(new StatusEntity(Long.valueOf("9999"), "Status"));
        expectedStatus.add(new StatusEntity(Long.valueOf("9999"), "Status"));
        when(statusService.getStatus()).thenReturn(expectedStatus);
        List<StatusEntity> actualStatus = statusController.getStatus();
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void testGetStatusById() {
        StatusEntity expectedStatus = new StatusEntity(Long.valueOf("9999"), "Status 1");
        when(statusService.getStatusById(expectedStatus.getStatusID())).thenReturn(Optional.of(expectedStatus));
        ResponseEntity<StatusEntity> response = statusController.getStatusById(expectedStatus.getStatusID());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedStatus, response.getBody());
    }

    @Test
    public void testGetStatusByIdNotFound() {
        long id = 9999L;
        when(statusService.getStatusById(id)).thenReturn(Optional.empty());
        ResponseEntity<StatusEntity> response = statusController.getStatusById(id);
        assertEquals(null, response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateStatusSuccess() {
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        when(statusService.validateStatusName(anyString())).thenReturn(Optional.empty());
        ResponseEntity<?> response = statusController.createStatus(status);
        verify(statusService, times(1)).validateStatusName(anyString());
        verify(statusService, times(1)).createStatus(status);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateStatusConflict() {
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        when(statusService.validateStatusName(anyString())).thenReturn(Optional.of(status));
        ResponseEntity<?> response = statusController.createStatus(status);
        verify(statusService, times(1)).validateStatusName(anyString());
        verify(statusService, never()).createStatus(status);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testUpdateUser() {
        Long id = Long.valueOf("9999");
        StatusEntity status = new StatusEntity(id, "Status 2");
        when(statusService.getStatusById(id)).thenReturn(Optional.of(new StatusEntity()));
        when(statusService.validateStatusName(status.getStatusName())).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = statusController.updateStatus(id, status);
        verify(statusService).getStatusById(id);
        verify(statusService).validateStatusName(status.getStatusName());
        verify(statusService).updateStatus(id, status);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

   @Test
   public void testUpdateUserNotFound() {
       long id = 9999L;
       StatusEntity status = new StatusEntity(Long.valueOf("9998"), "Status 2");
       when(statusService.getStatusById(id)).thenReturn(Optional.empty());
       ResponseEntity<?> response = statusController.updateStatus(id, status);
       assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
   }

    @Test
    public void testUpdateUserConflict() {
        long id = 9999L;
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        when(statusService.getStatusById(id)).thenReturn(Optional.of(new StatusEntity()));
        when(statusService.validateStatusName(status.getStatusName())).thenReturn(Optional.of(status));
        ResponseEntity<?> response = statusController.updateStatus(id, status);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void deleteStatus_shouldCallService() {
        statusController.deleteStatus();
        verify(statusService, times(1)).deleteStatus();
    }

    @Test
    void deleteStatusById_shouldReturnNotFound_whenStatusDoesNotExist() {
        Long statusId = Long.valueOf("9999");
        when(statusService.existsStatusById(statusId)).thenReturn(false);
        ResponseEntity<String> response = statusController.deleteStatusById(statusId);
        assertEquals(response.getStatusCodeValue(), 404);
        verify(statusService, never()).deleteStatusById(statusId);
    }

    @Test
    void deleteStatusById_shouldCallService_whenStatusExists() {
        Long statusId = 9999L;
        when(statusService.existsStatusById(statusId)).thenReturn(true);
        ResponseEntity<String> response = statusController.deleteStatusById(statusId);
        assertEquals(response.getStatusCodeValue(), 200);
        verify(statusService, times(1)).deleteStatusById(statusId);
    }
}
