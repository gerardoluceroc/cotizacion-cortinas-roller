package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.CoverageEntity;
import com.civilo.roller.controllers.CoverageController;
import com.civilo.roller.services.CoverageService;
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
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class CoverageControllerTest {
    @Mock
    private CoverageService coverageService;
    @InjectMocks
    private CoverageController coverageController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetCoverages() {
        List<CoverageEntity> expectedCoverages = new ArrayList<>();
        expectedCoverages.add(new CoverageEntity(1L, "Coverage 1"));
        expectedCoverages.add(new CoverageEntity(2L, "Coverage 2"));
        when(coverageService.getCoverages()).thenReturn(expectedCoverages);
        List<CoverageEntity> actualCoverages = coverageController.getCoverages();
        assertEquals(expectedCoverages, actualCoverages);
    }

    @Test
    void testSaveCoverage() {
        CoverageEntity expectedCoverage = new CoverageEntity(1L, "Coverage 1");
        when(coverageService.createCoverage(Mockito.any(CoverageEntity.class))).thenReturn(expectedCoverage);
        CoverageEntity actualCoverage = coverageController.saveCoverage(new CoverageEntity());
        assertEquals(expectedCoverage, actualCoverage);
    }

    @Test
    public void testDeleteCoverages() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("SE ELIMINARON LAS COMUNAS CORRECTAMENTE");
        Mockito.doNothing().when(coverageService).deleteCoverages();
        ResponseEntity<String> response = coverageController.deleteCoverages();
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testDeleteCoverageById() {
        Long id = Long.valueOf("9999");
        CoverageEntity coverage = new CoverageEntity(id, "Santiago");
        when(coverageService.existCoverageById(id)).thenReturn(true);
        Mockito.doNothing().when(coverageService).deleteCoverageById(id);
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("COMUNA CON ID " + id + " ELIMINADA CORRECTAMENTE \n");
        ResponseEntity<String> response = coverageController.deleteCoverageById(id);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testDeleteCoverageByIdNotFound() {
        Long id = Long.valueOf("9999");
        when(coverageService.existCoverageById(id)).thenReturn(false);
        ResponseEntity<String> expectedResponse = ResponseEntity.notFound().build();
        ResponseEntity<String> response = coverageController.deleteCoverageById(id);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testUpdateCoverageWithValidData() {
        long id = Long.valueOf("9999");
        CoverageEntity coverage = new CoverageEntity(id, "Santiago");
        when(coverageService.getCoverageById(id)).thenReturn(Optional.of(coverage));
        when(coverageService.validateCoverage(coverage.getCommune())).thenReturn(Optional.empty());
        ResponseEntity<?> response = coverageController.updateCoverage(id, coverage);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(coverageService, times(1)).updateCoverage(id, coverage);
    }

    @Test
    public void testUpdateCoverageWithNonExistingId() {
        long id = Long.valueOf("9999");
        CoverageEntity coverage = new CoverageEntity(id, "Santiago");
        when(coverageService.getCoverageById(id)).thenReturn(Optional.empty());
        ResponseEntity<?> response = coverageController.updateCoverage(id, coverage);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(coverageService, times(0)).updateCoverage(anyLong(), any(CoverageEntity.class));
    }

    @Test
    public void testUpdateCoverageWithDuplicateName() {
        long id = Long.valueOf("9999");
        CoverageEntity coverage = new CoverageEntity(id, "Santiago");
        when(coverageService.getCoverageById(id)).thenReturn(Optional.of(coverage));
        when(coverageService.validateCoverage(coverage.getCommune())).thenReturn(Optional.of(new CoverageEntity()));
        ResponseEntity<?> response = coverageController.updateCoverage(id, coverage);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(coverageService, times(0)).updateCoverage(anyLong(), any(CoverageEntity.class));
    }

    @Test
    void testCreateCoverageWithNewCommune() {
        CoverageEntity coverage = new CoverageEntity(1L, "Santiago");
        when(coverageService.validateCoverage(anyString())).thenReturn(Optional.empty());
        ResponseEntity<?> response = coverageController.createCoverage(coverage);
        verify(coverageService, times(1)).createCoverage(coverage);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateCoverageWithExistingCommune() {
        CoverageEntity coverage = new CoverageEntity(1L, "Santiago");
        when(coverageService.validateCoverage(anyString())).thenReturn(Optional.of(coverage));
        ResponseEntity<?> response = coverageController.createCoverage(coverage);
        verify(coverageService, never()).createCoverage(coverage);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testGetCoverageByIdSuccess() {
        CoverageEntity coverageEntity = new CoverageEntity(Long.valueOf("9999"), "Santiago");
        when(coverageService.getCoverageById(anyLong())).thenReturn(Optional.of(coverageEntity));
        ResponseEntity<CoverageEntity> response = coverageController.getCoverageById(9999L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coverageEntity, response.getBody());
    }

    @Test
    public void testGetCoverageByIdNotFound() {
        when(coverageService.getCoverageById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<CoverageEntity> response = coverageController.getCoverageById(9999L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}