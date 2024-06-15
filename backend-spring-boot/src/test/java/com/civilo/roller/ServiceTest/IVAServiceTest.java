package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.IVAEntity;
import com.civilo.roller.repositories.IVARepository;
import com.civilo.roller.services.IVAService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class IVAServiceTest {
    @Mock
    private IVARepository ivaRepository;

    @InjectMocks
    private IVAService ivaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetIVAPercentage() {
        List<IVAEntity> ivaEntityList = new ArrayList<>();
        ivaEntityList.add(new IVAEntity(1L, 19.0f));
        ivaEntityList.add(new IVAEntity(2L, 21.0f));
        when(ivaRepository.findAll()).thenReturn(ivaEntityList);

        float result = ivaService.getIVAPercentage();

        assertEquals(21.0f, result); // Verifica que el resultado sea el porcentaje de IVA esperado
    }

    @Test
    public void testGetIVAPercentageEmptyList() {
        List<IVAEntity> ivaEntityList = new ArrayList<>();
        when(ivaRepository.findAll()).thenReturn(ivaEntityList);

        float result = ivaService.getIVAPercentage();

        assertEquals(0.0f, result); // Verifica que el resultado sea 0 cuando la lista está vacía
    }

    @Test
    public void testGetLastIVA() {
        List<IVAEntity> ivaEntityList = new ArrayList<>();
        ivaEntityList.add(new IVAEntity(1L, 10f));
        ivaEntityList.add(new IVAEntity(2L, 20f));
        ivaEntityList.add(new IVAEntity(3L, 30f));
        when(ivaRepository.findAll()).thenReturn(ivaEntityList);
        IVAEntity result = ivaService.getLastIVA();
        assertNotNull(result);
        assertEquals(30f, result.getIvaPercentage());
    }

    @Test
    public void testGetIVAByPercentage_NonExistingPercentage() {
        List<IVAEntity> ivaEntityList = new ArrayList<>();
        ivaEntityList.add(new IVAEntity(1L, 10f));
        ivaEntityList.add(new IVAEntity(2L, 20f));
        ivaEntityList.add(new IVAEntity(3L, 30f));
        when(ivaRepository.findAll()).thenReturn(ivaEntityList);
        IVAEntity result = ivaService.getIVAByPercentage(0.4f);

        assertNull(result);
    }

    @Test
    public void testGetIVAByPercentage_ExistingPercentage() {
        List<IVAEntity> ivaEntityList = new ArrayList<>();
        ivaEntityList.add(new IVAEntity(1L, 10f));
        ivaEntityList.add(new IVAEntity(2L, 20f));
        ivaEntityList.add(new IVAEntity(3L, 30f));
        when(ivaRepository.findAll()).thenReturn(ivaEntityList);
        IVAEntity result = ivaService.getIVAByPercentage(30f);
        assertNotNull(result);
        assertEquals(30f,result.getIvaPercentage());
    }

    @Test
    public void testGetIVAs() {
        IVAEntity iva1 = new IVAEntity(1L, 0.16f);
        IVAEntity iva2 = new IVAEntity(2L, 0.12f);
        List<IVAEntity> mockIvas = Arrays.asList(iva1, iva2);
        when(ivaRepository.findAll()).thenReturn(mockIvas);
        List<IVAEntity> result = ivaService.getIVAs();
        assertEquals(mockIvas, result);
    }

    @Test
    public void testGetIVAById() {
        Long ivaId = 1L;
        IVAEntity mockIva = new IVAEntity(ivaId, 0.16f);
        when(ivaRepository.findById(ivaId)).thenReturn(Optional.of(mockIva));
        Optional<IVAEntity> result = ivaService.getIVAById(ivaId);
        assertTrue(result.isPresent());
        assertEquals(mockIva, result.get());
    }

    @Test
    public void testCreateIVA() {
        IVAEntity savedIVA = new IVAEntity(1L, 10.0f);
        Mockito.when(ivaRepository.save(any(IVAEntity.class))).thenReturn(savedIVA);
        IVAEntity newIVA = new IVAEntity(1L, 10.0f);
        IVAEntity result = ivaService.createIVA(newIVA);
        assertEquals(savedIVA, result);
    }

    @Test
    public void testUpdateIVA() {
        IVAEntity existingIVA = new IVAEntity(1L, 10.0f);
        Mockito.when(ivaRepository.findById(1L)).thenReturn(Optional.of(existingIVA));
        Mockito.when(ivaRepository.save(any(IVAEntity.class))).thenReturn(existingIVA);
        IVAEntity updatedIVA = new IVAEntity(1L, 15.0f);
        IVAEntity result = ivaService.updateIVA(1L, updatedIVA);
        assertEquals(updatedIVA, result);
        assertEquals(updatedIVA.getIvaPercentage(), result.getIvaPercentage());
    }

    @Test
    public void testDeleteIVAs() {
        ivaService.deleteIVAs();
        Mockito.verify(ivaRepository, Mockito.times(1)).deleteAll();
    }

    @Test
    public void testDeleteIVAById() {
        ivaService.deleteIVAById(1L);
        Mockito.verify(ivaRepository, Mockito.times(1)).deleteById(eq(1L));
    }

    @Test
    public void testExistsIVAById() {
        Mockito.when(ivaRepository.findById(1L)).thenReturn(Optional.of(new IVAEntity(1L, 10.0f)));
        Mockito.when(ivaRepository.findById(2L)).thenReturn(Optional.empty());
        boolean exists1 = ivaService.existsIVAById(1L);
        boolean exists2 = ivaService.existsIVAById(2L);
        assertTrue(exists1);
        assertFalse(exists2);
    }
}