package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.ProfitMarginEntity;
import com.civilo.roller.controllers.ProfitMarginController;
import com.civilo.roller.repositories.ProfitMarginRepository;
import com.civilo.roller.services.ProfitMarginService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class ProfitMarginControllerTest {
    @Mock
    private ProfitMarginService profitMarginService;
    @InjectMocks
    private ProfitMarginController profitMarginController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProfitMargins() {
        List<ProfitMarginEntity> expectedProfitMargins = new ArrayList<>();
        expectedProfitMargins.add(new ProfitMarginEntity(1L, 1, 1));
        expectedProfitMargins.add(new ProfitMarginEntity(2L, 1, 1));
        Mockito.when(profitMarginService.getProfitMargins()).thenReturn(expectedProfitMargins);
        List<ProfitMarginEntity> actualProfitMargin = profitMarginController.getProfitMargins();
        assertEquals(expectedProfitMargins, actualProfitMargin);
    }

    @Test
    public void testGetProfitMarginById_ExistingId_ReturnsProfitMarginEntity() {
        // Arrange
        Long id = 1L;
        ProfitMarginEntity expectedProfitMargin = new ProfitMarginEntity();
        when(profitMarginService.getProfitMarginById(id)).thenReturn(Optional.of(expectedProfitMargin));

        // Act
        ResponseEntity<ProfitMarginEntity> response = profitMarginController.getProfitMarginById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedProfitMargin, response.getBody());
    }

    @Test
    public void testGetProfitMarginById_NonExistingId_ReturnsNotFound() {
        // Arrange
        Long id = 1L;
        when(profitMarginService.getProfitMarginById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ProfitMarginEntity> response = profitMarginController.getProfitMarginById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateProfitMargin_NewProfitMargin_ReturnsOk() {
        // Arrange
        ProfitMarginEntity profitMarginToCreate = new ProfitMarginEntity();
        when(profitMarginService.validateDecimalProfitMargin(anyFloat())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = profitMarginController.createProfitMargin(profitMarginToCreate);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateProfitMargin_ExistingProfitMargin_ReturnsConflict() {
        // Arrange
        ProfitMarginEntity existingProfitMargin = new ProfitMarginEntity();
        when(profitMarginService.validateDecimalProfitMargin(anyFloat())).thenReturn(Optional.of(existingProfitMargin));

        // Act
        ResponseEntity<?> response = profitMarginController.createProfitMargin(existingProfitMargin);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testUpdateProfitMargin_ExistingId_ReturnsOkResponseEntity() {
        // Arrange
        long id = 1L;
        ProfitMarginEntity profitMarginToUpdate = new ProfitMarginEntity();
        when(profitMarginService.getProfitMarginById(id)).thenReturn(Optional.of(profitMarginToUpdate));
        when(profitMarginService.validateDecimalProfitMargin(anyFloat())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = profitMarginController.updateProfitMargin(id, profitMarginToUpdate);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateProfitMargin_NonExistingId_ReturnsConflictResponseEntity() {
        // Arrange
        long id = 1L;
        ProfitMarginEntity profitMarginToUpdate = new ProfitMarginEntity();
        when(profitMarginService.getProfitMarginById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = profitMarginController.updateProfitMargin(id, profitMarginToUpdate);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El margen de utilidad con el ID especificado no existe.", response.getBody());
    }

    @Test
    public void testUpdateProfitMargin_DuplicateDecimal_ReturnsConflictResponseEntity() {
        // Arrange
        long id = 1L;
        ProfitMarginEntity profitMarginToUpdate = new ProfitMarginEntity();
        float existingDecimalProfitMargin = 0.2f;
        profitMarginToUpdate.setDecimalProfitMargin(existingDecimalProfitMargin);
        when(profitMarginService.getProfitMarginById(id)).thenReturn(Optional.of(new ProfitMarginEntity()));
        when(profitMarginService.validateDecimalProfitMargin(existingDecimalProfitMargin)).thenReturn(Optional.of(new ProfitMarginEntity()));

        // Act
        ResponseEntity<?> response = profitMarginController.updateProfitMargin(id, profitMarginToUpdate);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El margen de utilidad ingresado ya existe", response.getBody());
    }

    @Test
    public void testDeleteProfitMargins() {
        // Arrange

        // Act
        ResponseEntity<String> response = profitMarginController.deleteProfitMargins();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SE ELIMINARON LOS MERGENES DE UTILIDAD CORRECTAMENTE", response.getBody());

        // Verificamos que el método del servicio haya sido llamado
        verify(profitMarginService, times(1)).deleteProfitMargins();
    }

    @Test
    public void testDeleteProfitMarginById_ExistingId() {
        // Arrange
        Long id = 1L;
        when(profitMarginService.existsProfitMarginById(id)).thenReturn(true);

        // Act
        ResponseEntity<String> response = profitMarginController.deleteProfitMarginById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("MARGEN DE UTILIDAD CON ID " + id + " ELIMINADO CORRECTAMENTE\n", response.getBody());

        // Verificamos que el método del servicio haya sido llamado
        verify(profitMarginService, times(1)).deleteProfitMarginById(id);
    }

    @Test
    public void testDeleteProfitMarginById_NonExistingId() {
        // Arrange
        Long id = 1L;
        when(profitMarginService.existsProfitMarginById(id)).thenReturn(false);

        // Act
        ResponseEntity<String> response = profitMarginController.deleteProfitMarginById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        // Verificamos que el método del servicio no haya sido llamado
        verify(profitMarginService, never()).deleteProfitMarginById(id);
    }

}
