package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.CoverageEntity;
import com.civilo.roller.exceptions.EntityNotFoundException;
import com.civilo.roller.repositories.CoverageRepository;
import com.civilo.roller.services.CoverageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class CoverageServiceTest {
    @Mock
    private CoverageRepository coverageRepository;

    @InjectMocks
    CoverageService coverageService;

    @Test
    void saveCoverage(){
        CoverageEntity coverage = new CoverageEntity(Long.valueOf("9999"), "Arica");
        when(coverageRepository.save(coverage)).thenReturn(coverage);
        final CoverageEntity currentResponse = coverageService.createCoverage(coverage);
        assertEquals(coverage,currentResponse);
    }

    @Test
    void getCoverages(){
        CoverageEntity coverage = new CoverageEntity(Long.valueOf("9999"), "Arica");
        List<CoverageEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(coverage);
        when((List<CoverageEntity>) coverageRepository.findAll()).thenReturn(expectedAnswer);
        final List<CoverageEntity> currentResponse = coverageService.getCoverages();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    void testGetCoverageById() {
        Long id = 1L;
        CoverageEntity coverageEntity = new CoverageEntity(id, "Santiago");
        when(coverageRepository.findById(id)).thenReturn(Optional.of(coverageEntity));
        Optional<CoverageEntity> result = coverageService.getCoverageById(id);
        assertEquals(coverageEntity, result.get());
    }

    @Test
    void testValidateCoverage() {
        String commune = "Santiago";
        CoverageEntity coverage = new CoverageEntity(Long.valueOf("9999"), "Santiago");
        when(coverageRepository.findByCommune(commune)).thenReturn(coverage);
        Optional<CoverageEntity> result = coverageService.validateCoverage(commune);
        assertTrue(result.isPresent());
        assertEquals(result.get(), coverage);
        verify(coverageRepository, times(1)).findByCommune(commune);
    }

    @Test
    void testUpdateCoverage() {
        Long coverageId = Long.valueOf("9999");
        CoverageEntity existingCoverage = new CoverageEntity(coverageId, "Santiago");
        CoverageEntity updatedCoverage = new CoverageEntity(coverageId, "Providencia");
        when(coverageRepository.findById(coverageId)).thenReturn(Optional.of(existingCoverage));
        when(coverageRepository.save(existingCoverage)).thenReturn(updatedCoverage);

        CoverageEntity result = coverageService.updateCoverage(coverageId, updatedCoverage);

        assertEquals(result, updatedCoverage);
        verify(coverageRepository, times(1)).findById(coverageId);
        verify(coverageRepository, times(1)).save(existingCoverage);
    }

    @Test
    void testUpdateCoverage_EntityNotFoundException() {
        Long coverageId = Long.valueOf("9999");
        CoverageEntity updatedCoverage = new CoverageEntity(coverageId, "Providencia");
        when(coverageRepository.findById(coverageId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> coverageService.updateCoverage(coverageId, updatedCoverage));
        verify(coverageRepository, times(1)).findById(coverageId);
        verify(coverageRepository, never()).save(any());
    }

    @Test
    void deleteCoverages() {
        coverageService.deleteCoverages();
        verify(coverageRepository, times(1)).deleteAll();
    }

    @Test
    void deleteCoverageById() {
        Long idToDelete = Long.valueOf("9999");
        coverageService.deleteCoverageById(idToDelete);
        verify(coverageRepository, times(1)).deleteById(idToDelete);
    }

    @Test
    void testExistCoverageById() {
        Long id = Long.valueOf("9999");
        when(coverageRepository.findById(id)).thenReturn(Optional.of(new CoverageEntity(id, "Santiago")));
        assertTrue(coverageService.existCoverageById(id));
    }

    @Test
    void testGetCoverage() {
        Long id = Long.valueOf("9999");
        when(coverageRepository.findById(id)).thenReturn(Optional.of(new CoverageEntity(id, "Santiago")));
        assertEquals("Santiago", coverageService.getCoverage(id).get().getCommune());
    }

    @Test
    void testGetCoverageIdByCommune() {
        String commune = "Santiago";
        when(coverageRepository.findByCommune(commune)).thenReturn(new CoverageEntity(Long.valueOf("9999"), commune));
        assertEquals(Long.valueOf("9999"), coverageService.getCoverageIdByCommune(commune));
    }

}
