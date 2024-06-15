package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.ProfitMarginEntity;
import com.civilo.roller.repositories.ProfitMarginRepository;
import com.civilo.roller.services.ProfitMarginService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProfitMarginServiceTest {
    @Mock
    private ProfitMarginRepository profitMarginRepository;

    @InjectMocks
    private ProfitMarginService profitMarginService;

    @Test
    public void testGetLastProfitMargin() {
        ProfitMarginEntity profitMarginEntity = new ProfitMarginEntity(1L, 40f, 0.4f);
        when(profitMarginRepository.findAll()).thenReturn(List.of(profitMarginEntity));
        ProfitMarginEntity lastProfitMargin = profitMarginService.getLastProfitMargin();
        assertEquals(profitMarginEntity, lastProfitMargin);
    }

    @Test
    public void testGetProfitMargins() {
        List<ProfitMarginEntity> profitMargins = new ArrayList<>();
        profitMargins.add(new ProfitMarginEntity(1L, 30f, 0.3f));
        profitMargins.add(new ProfitMarginEntity(2L, 40f, 0.4f));
        profitMargins.add(new ProfitMarginEntity(3L, 50f, 0.5f));
        when(profitMarginRepository.findAll()).thenReturn(profitMargins);
        List<ProfitMarginEntity> returnedProfitMargins = profitMarginService.getProfitMargins();
        assertEquals(profitMargins, returnedProfitMargins);
    }

    @Test
    public void testGetProfitMarginById_ExistingId_ReturnsProfitMarginEntity() {
        Long id = 1L;
        ProfitMarginEntity expectedProfitMargin = new ProfitMarginEntity();
        when(profitMarginRepository.findById(id)).thenReturn(Optional.of(expectedProfitMargin));
        Optional<ProfitMarginEntity> result = profitMarginService.getProfitMarginById(id);
        assertTrue(result.isPresent());
        assertEquals(expectedProfitMargin, result.get());
    }

    @Test
    public void testGetProfitMarginById_NonExistingId_ReturnsEmptyOptional() {
        Long id = 1L;
        when(profitMarginRepository.findById(id)).thenReturn(Optional.empty());
        Optional<ProfitMarginEntity> result = profitMarginService.getProfitMarginById(id);
        assertFalse(result.isPresent());
    }

    @Test
    public void testCreateProfitMargin() {
        ProfitMarginEntity profitMarginToCreate = new ProfitMarginEntity();
        ProfitMarginEntity createdProfitMargin = new ProfitMarginEntity();
        when(profitMarginRepository.save(any(ProfitMarginEntity.class))).thenReturn(createdProfitMargin);
        ProfitMarginEntity result = profitMarginService.createProfitMargin(profitMarginToCreate);
        assertNotNull(result);
        assertEquals(createdProfitMargin, result);
    }

    @Test
    public void testValidateDecimalProfitMargin_ExistingDecimal_ReturnsProfitMarginEntity() {
        float decimalProfitMargin = 0.2f;
        ProfitMarginEntity expectedProfitMargin = new ProfitMarginEntity();
        when(profitMarginRepository.findByDecimalProfitMargin(decimalProfitMargin)).thenReturn(expectedProfitMargin);
        Optional<ProfitMarginEntity> result = profitMarginService.validateDecimalProfitMargin(decimalProfitMargin);
        assertTrue(result.isPresent());
        assertEquals(expectedProfitMargin, result.get());
    }

    @Test
    public void testValidateDecimalProfitMargin_NonExistingDecimal_ReturnsEmptyOptional() {
        float decimalProfitMargin = 0.2f;
        when(profitMarginRepository.findByDecimalProfitMargin(decimalProfitMargin)).thenReturn(null);
        Optional<ProfitMarginEntity> result = profitMarginService.validateDecimalProfitMargin(decimalProfitMargin);
        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateProfitMargin() {
        Long profitMarginID = 1L;
        ProfitMarginEntity existingProfitMargin = new ProfitMarginEntity();
        existingProfitMargin.setProfitMarginID(profitMarginID);
        ProfitMarginEntity updatedProfitMargin = new ProfitMarginEntity();
        updatedProfitMargin.setProfitMarginID(profitMarginID);
        when(profitMarginRepository.findById(profitMarginID)).thenReturn(Optional.of(existingProfitMargin));
        when(profitMarginRepository.save(existingProfitMargin)).thenReturn(updatedProfitMargin);
        ProfitMarginEntity result = profitMarginService.updateProfitMargin(profitMarginID, updatedProfitMargin);
        assertEquals(updatedProfitMargin, result);
    }

    @Test
    public void testDeleteProfitMargins() {
        profitMarginService.deleteProfitMargins();
        verify(profitMarginRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteProfitMarginById() {
        Long id = 1L;
        profitMarginService.deleteProfitMarginById(id);
        verify(profitMarginRepository, times(1)).deleteById(id);
    }

    @Test
    public void testExistsProfitMarginById() {
        Long id = 1L;
        when(profitMarginRepository.findById(id)).thenReturn(Optional.of(new ProfitMarginEntity()));
        boolean result = profitMarginService.existsProfitMarginById(id);
        assertTrue(result);
    }
}