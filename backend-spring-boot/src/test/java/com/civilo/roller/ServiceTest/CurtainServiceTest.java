package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.CurtainEntity;
import com.civilo.roller.repositories.CurtainRepository;
import com.civilo.roller.services.CurtainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
public class CurtainServiceTest {

    @Mock
    private CurtainRepository curtainRepository;

    @InjectMocks
    CurtainService curtainService;

    @Test
    void saveCurtain(){
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Roller");
        when(curtainRepository.save(curtain)).thenReturn(curtain);
        final CurtainEntity currentResponse = curtainService.createCurtain(curtain);
        assertEquals(curtain,currentResponse);
    }

    @Test
    void getCurtains(){
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Roller");
        List<CurtainEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(curtain);
        when((List<CurtainEntity>) curtainRepository.findAll()).thenReturn(expectedAnswer);
        final List<CurtainEntity> currentResponse = curtainService.getCurtains();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    public void testGetCurtainById() {
        Long curtainId = 1L;
        CurtainEntity curtainEntity = new CurtainEntity(curtainId, "Type1");
        when(curtainRepository.findById(curtainId)).thenReturn(Optional.of(curtainEntity));
        Optional<CurtainEntity> result = curtainService.getCurtainById(curtainId);
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(curtainEntity, result.get());
    }

    @Test
    public void testValidateCurtain() {
        String curtainName = "Type1";
        CurtainEntity curtainEntity = new CurtainEntity(1L, curtainName);
        when(curtainRepository.findByCurtainType(curtainName)).thenReturn(curtainEntity);
        Optional<CurtainEntity> result = curtainService.validateCurtain(curtainName);
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(curtainEntity, result.get());
    }

    @Test
    public void testUpdateCurtain() {
        Long curtainId = 1L;
        CurtainEntity existingCurtainEntity = new CurtainEntity(curtainId, "Type1");
        CurtainEntity updatedCurtainEntity = new CurtainEntity(curtainId, "UpdatedType");
        when(curtainRepository.findById(curtainId)).thenReturn(Optional.of(existingCurtainEntity));
        when(curtainRepository.save(existingCurtainEntity)).thenReturn(updatedCurtainEntity);
        CurtainEntity curtainToUpdate = new CurtainEntity(curtainId, "UpdatedType");
        CurtainEntity result = curtainService.updateCurtain(curtainId, curtainToUpdate);
        assertNotNull(result);
        assertEquals("UpdatedType", result.getCurtainType());
    }

    @Test
    public void testDeleteCurtains() {
        curtainService.deleteCurtains();
        verify(curtainRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteCurtainById() {
        Long curtainId = 1L;
        curtainService.deleteCurtainById(curtainId);
        verify(curtainRepository, times(1)).deleteById(curtainId);
    }

    @Test
    public void testExistCurtainById() {
        Long curtainId = 1L;
        CurtainEntity curtainEntity = new CurtainEntity(curtainId, "Type1");
        when(curtainRepository.findById(curtainId)).thenReturn(Optional.of(curtainEntity));
        boolean result = curtainService.existCurtainById(curtainId);
        assertTrue(result);
    }
}
