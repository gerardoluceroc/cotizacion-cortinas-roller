package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.CurtainEntity;
import com.civilo.roller.controllers.CurtainController;
import com.civilo.roller.services.CurtainService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class CurtainControllerTest {
    @Mock
    private CurtainService curtainService;
    @InjectMocks
    private CurtainController curtainController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCurtains() {
        List<CurtainEntity> expectedCurtains = new ArrayList<>();
        expectedCurtains.add(new CurtainEntity(1L, "Curtain 1"));
        expectedCurtains.add(new CurtainEntity(2L, "Curtain 2"));
        when(curtainService.getCurtains()).thenReturn(expectedCurtains);
        List<CurtainEntity> actualCurtain = curtainController.getCurtains();
        assertEquals(expectedCurtains, actualCurtain);
    }

    @Test
    public void testGetCurtainById_ExistingId_ReturnsCurtainEntity() {
        long id = 1L;
        CurtainEntity expectedCurtain = new CurtainEntity();
        when(curtainService.getCurtainById(id)).thenReturn(Optional.of(expectedCurtain));
        ResponseEntity<CurtainEntity> result = curtainController.getCurtainById(id);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedCurtain, result.getBody());
    }

    @Test
    public void testGetCurtainById_NonExistingId_ReturnsNotFound() {
        long id = 1L;
        when(curtainService.getCurtainById(id)).thenReturn(Optional.empty());
        ResponseEntity<CurtainEntity> result = curtainController.getCurtainById(id);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    public void testSaveCurtain() {
        CurtainEntity curtainToSave = new CurtainEntity();
        CurtainEntity savedCurtain = new CurtainEntity();
        when(curtainService.createCurtain(any(CurtainEntity.class))).thenReturn(savedCurtain);
        CurtainEntity result = curtainController.saveCurtain(curtainToSave);
        assertNotNull(result);
        assertEquals(savedCurtain, result);
    }

    @Test
    public void testCreateCurtain_NewCurtain_ReturnsOk() {
        CurtainEntity curtainToCreate = new CurtainEntity();
        when(curtainService.validateCurtain(curtainToCreate.getCurtainType())).thenReturn(Optional.empty());
        ResponseEntity<?> result = curtainController.createCurtain(curtainToCreate);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    public void testCreateCurtain_ExistingCurtain_ReturnsConflict() {
        CurtainEntity existingCurtain = new CurtainEntity();
        when(curtainService.validateCurtain(existingCurtain.getCurtainType())).thenReturn(Optional.of(existingCurtain));
        ResponseEntity<?> result = curtainController.createCurtain(existingCurtain);
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    public void testUpdateCurtain_CurtainExistsAndNameNotInUse_ReturnsOkResponse() {
        long id = 1L;
        CurtainEntity curtainToUpdate = new CurtainEntity();
        curtainToUpdate.setCurtainType("New Curtain Type");
        when(curtainService.getCurtainById(id)).thenReturn(Optional.of(new CurtainEntity()));
        when(curtainService.validateCurtain(anyString())).thenReturn(Optional.empty());
        ResponseEntity<?> response = curtainController.updateCurtain(id, curtainToUpdate);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateCurtain_CurtainNotExists_ReturnsNotFoundResponse() {
        long id = 1L;
        CurtainEntity curtainToUpdate = new CurtainEntity();
        curtainToUpdate.setCurtainType("New Curtain Type");
        when(curtainService.getCurtainById(id)).thenReturn(Optional.empty());
        ResponseEntity<?> response = curtainController.updateCurtain(id, curtainToUpdate);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateCurtain_CurtainNameInUse_ReturnsConflictResponse() {
        long id = 1L;
        CurtainEntity curtainToUpdate = new CurtainEntity();
        curtainToUpdate.setCurtainType("New Curtain Type");
        when(curtainService.getCurtainById(id)).thenReturn(Optional.of(new CurtainEntity()));
        when(curtainService.validateCurtain(anyString())).thenReturn(Optional.of(new CurtainEntity()));
        ResponseEntity<?> response = curtainController.updateCurtain(id, curtainToUpdate);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testDeleteCurtains_ReturnsOkResponse() {
        ResponseEntity<String> response = curtainController.deleteCurtains();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SE ELIMINARON LAS CORTINAS CORRECTAMENTE", response.getBody());
    }

    @Test
    public void testDeleteCurtainById_CurtainExists_ReturnsOkResponse() {
        long id = 1L;
        when(curtainService.existCurtainById(id)).thenReturn(true);
        ResponseEntity<String> response = curtainController.deleteCurtainById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("CORTINA CON ID " + id + " ELIMINADA CORRECTAMENTE \n", response.getBody());
    }

    @Test
    public void testDeleteCurtainById_CurtainNotExists_ReturnsNotFoundResponse() {
        long id = 1L;
        when(curtainService.existCurtainById(id)).thenReturn(false);
        ResponseEntity<String> response = curtainController.deleteCurtainById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}