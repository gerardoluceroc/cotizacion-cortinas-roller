package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.IVAEntity;
import com.civilo.roller.controllers.IVAController;
import com.civilo.roller.services.IVAService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class IVAControllerTest {
    @Mock
    private IVAService ivaService;
    @InjectMocks
    private IVAController ivaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getIVA() {
        List<IVAEntity> expectedIVA = new ArrayList<>();
        expectedIVA.add(new IVAEntity(1L, 19f));
        when(ivaService.getIVAPercentage()).thenReturn(expectedIVA.get(0).getIvaPercentage());
        float actualIVA = ivaController.getIVAPercentage();
        assertEquals(expectedIVA.get(0).getIvaPercentage(), actualIVA);
    }

    @Test
    public void testGetIVAs_ReturnsListOfIVAs() {
        List<IVAEntity> expectedIVAs = new ArrayList<>();
        expectedIVAs.add(new IVAEntity(1L, 0.1f));
        expectedIVAs.add(new IVAEntity(2L, 0.2f));
        when(ivaService.getIVAs()).thenReturn(expectedIVAs);
        List<IVAEntity> result = ivaController.getIVAs();
        assertEquals(expectedIVAs, result);
    }

    @Test
    public void testGetIVAById_ExistingId_ReturnsIVAEntity() {
        long id = 1L;
        IVAEntity expectedIVA = new IVAEntity(id, 0.1f);
        when(ivaService.getIVAById(id)).thenReturn(Optional.of(expectedIVA));
        ResponseEntity<IVAEntity> result = ivaController.getIVAById(id);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedIVA, result.getBody());
    }

    @Test
    public void testGetIVAById_NonExistingId_ReturnsNotFound() {
        long id = 1L;
        when(ivaService.getIVAById(id)).thenReturn(Optional.empty());
        ResponseEntity<IVAEntity> result = ivaController.getIVAById(id);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    public void testCreateIVA() {
        IVAEntity ivaToCreate = new IVAEntity(1L, 0.1f);
        ResponseEntity<?> result = ivaController.createIVA(ivaToCreate);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testUpdateIVA_ExistingId_ReturnsOk() {
        long id = 1L;
        IVAEntity ivaToUpdate = new IVAEntity(id, 0.2f);
        when(ivaService.getIVAById(id)).thenReturn(Optional.of(ivaToUpdate));
        ResponseEntity<?> result = ivaController.updateIVA(id, ivaToUpdate);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testUpdateIVA_NonExistingId_ReturnsNotFound() {
        long id = 1L;
        IVAEntity ivaToUpdate = new IVAEntity(id, 0.2f);
        when(ivaService.getIVAById(id)).thenReturn(Optional.empty());
        ResponseEntity<?> result = ivaController.updateIVA(id, ivaToUpdate);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeleteIVAs() {
        ResponseEntity<String> result = ivaController.deleteIVAs();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("SE ELIMINARON LOS IVAS CORRECTAMENTE", result.getBody());
    }

    @Test
    public void testDeleteIVAById_ExistingId_ReturnsOk() {
        long id = 1L;
        when(ivaService.existsIVAById(id)).thenReturn(true);
        ResponseEntity<String> result = ivaController.deleteIVAById(id);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("IVA CON ID " + id + " ELIMINADO CORRECTAMENTE\n", result.getBody());
    }

    @Test
    public void testDeleteIVAById_NonExistingId_ReturnsNotFound() {
        long id = 1L;
        when(ivaService.existsIVAById(id)).thenReturn(false);
        ResponseEntity<String> result = ivaController.deleteIVAById(id);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
