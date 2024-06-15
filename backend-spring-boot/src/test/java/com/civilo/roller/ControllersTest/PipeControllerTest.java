package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.PipeEntity;
import com.civilo.roller.controllers.PipeController;
import com.civilo.roller.services.PipeService;
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
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class PipeControllerTest {
    @Mock
    private PipeService pipeService;
    @InjectMocks
    private PipeController pipeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPipes() {
        List<PipeEntity> expectedPipes = new ArrayList<>();
        expectedPipes.add(new PipeEntity(1L, "Pipe 1"));
        expectedPipes.add(new PipeEntity(2L, "Pipe 2"));
        when(pipeService.getPipes()).thenReturn(expectedPipes);
        List<PipeEntity> actualPipe = pipeController.getPipes();
        assertEquals(expectedPipes, actualPipe);
    }

    @Test
    public void testGetPipeById_ExistingId_ReturnsPipeEntity() {
        // Arrange
        long id = 1L;
        PipeEntity expectedPipe = new PipeEntity();
        when(pipeService.getPipe(id)).thenReturn(Optional.of(expectedPipe));

        // Act
        ResponseEntity<PipeEntity> result = pipeController.getPipeById(id);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedPipe, result.getBody());
    }

    @Test
    public void testGetPipeById_NonExistingId_ReturnsNotFoundResponse() {
        // Arrange
        long id = 1L;
        when(pipeService.getPipe(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<PipeEntity> result = pipeController.getPipeById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    public void testCreatePipe_NewPipe_ReturnsOkResponse() {
        // Arrange
        PipeEntity pipeToCreate = new PipeEntity();
        when(pipeService.validatePipeName(pipeToCreate.getPipeName())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> result = pipeController.createPipe(pipeToCreate);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    public void testCreatePipe_ExistingPipe_ReturnsConflictResponse() {
        // Arrange
        PipeEntity existingPipe = new PipeEntity();
        PipeEntity pipeToCreate = new PipeEntity();
        when(pipeService.validatePipeName(pipeToCreate.getPipeName())).thenReturn(Optional.of(existingPipe));

        // Act
        ResponseEntity<?> result = pipeController.createPipe(pipeToCreate);

        // Assert
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("El tubo ingresado ya existe", result.getBody());
    }

    @Test
    public void testUpdatePipe_ExistingIdAndUniquePipeName_ReturnsOkResponse() {
        // Arrange
        long id = 1L;
        PipeEntity existingPipe = new PipeEntity();
        PipeEntity updatedPipe = new PipeEntity();
        when(pipeService.getPipe(id)).thenReturn(Optional.of(existingPipe));
        when(pipeService.validatePipeName(updatedPipe.getPipeName())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> result = pipeController.updatePipe(id, updatedPipe);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    public void testUpdatePipe_NonExistingId_ReturnsNotFoundResponse() {
        // Arrange
        long id = 1L;
        PipeEntity updatedPipe = new PipeEntity();
        when(pipeService.getPipe(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> result = pipeController.updatePipe(id, updatedPipe);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("El tubo con el ID especificado no existe.", result.getBody());
    }

    @Test
    public void testUpdatePipe_ExistingIdAndDuplicatePipeName_ReturnsConflictResponse() {
        // Arrange
        long id = 1L;
        PipeEntity existingPipe = new PipeEntity();
        PipeEntity updatedPipe = new PipeEntity();
        when(pipeService.getPipe(id)).thenReturn(Optional.of(existingPipe));
        when(pipeService.validatePipeName(updatedPipe.getPipeName())).thenReturn(Optional.of(existingPipe));

        // Act
        ResponseEntity<?> result = pipeController.updatePipe(id, updatedPipe);

        // Assert
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("El tubo ingresado ya existe", result.getBody());
    }

    @Test
    public void testDeletePipes() {
        // Arrange

        // Act
        ResponseEntity<String> response = pipeController.deletePipes();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SE ELIMINARON LOS TUBOS CORRECTAMENTE", response.getBody());
        verify(pipeService, times(1)).deletePipes();
    }

    @Test
    public void testDeletePipeById_ExistingPipe_ReturnsOkResponse() {
        // Arrange
        Long pipeId = 1L;
        when(pipeService.existsPipeById(pipeId)).thenReturn(true);

        // Act
        ResponseEntity<String> response = pipeController.deletePipeById(pipeId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TUBO CON ID 1 ELIMINADO CORRECTAMENTE\n", response.getBody());
        verify(pipeService, times(1)).deletePipeById(pipeId);
    }

    @Test
    public void testDeletePipeById_NonExistingPipe_ReturnsNotFoundResponse() {
        // Arrange
        Long pipeId = 1L;
        when(pipeService.existsPipeById(pipeId)).thenReturn(false);

        // Act
        ResponseEntity<String> response = pipeController.deletePipeById(pipeId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(pipeService, times(0)).deletePipeById(pipeId);
    }
}
