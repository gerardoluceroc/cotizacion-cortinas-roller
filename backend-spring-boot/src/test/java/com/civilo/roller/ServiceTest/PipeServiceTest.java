package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.PipeEntity;
import com.civilo.roller.repositories.PipeRepository;
import com.civilo.roller.services.PipeService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class PipeServiceTest {
    @Mock
    private PipeRepository pipeRepository;

    @InjectMocks
    PipeService pipeService;

    @Test
    void getPipes(){
        PipeEntity pipe = new PipeEntity(1L, "Pipe 1");
        List<PipeEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(pipe);
        when((List<PipeEntity>) pipeRepository.findAll()).thenReturn(expectedAnswer);
        final List<PipeEntity> currentResponse = pipeService.getPipes();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    void testGetPipeById() {
        Long id = 1L;
        PipeEntity expectedPipe = new PipeEntity(id, "Pipe 1");
        when(pipeRepository.findById(id)).thenReturn(Optional.of(expectedPipe));
        Optional<PipeEntity> actualPipe = pipeService.getPipe(id);
        assertEquals(expectedPipe, actualPipe.orElse(null));
    }

    @Test
    public void testCreatePipe() {
        PipeEntity pipeEntity = new PipeEntity();
        pipeEntity.setPipeID(1L);
        pipeEntity.setPipeName("Test Pipe");
        when(pipeRepository.save(any(PipeEntity.class))).thenReturn(pipeEntity);
        PipeEntity result = pipeService.createPipe(pipeEntity);
        verify(pipeRepository).save(pipeEntity);
        assertEquals(pipeEntity, result);
    }

    @Test
    public void testValidatePipeName() {
        String pipeName = "Test Pipe";
        when(pipeRepository.findByPipeName(pipeName)).thenReturn(new PipeEntity());
        Optional<PipeEntity> result = pipeService.validatePipeName(pipeName);
        verify(pipeRepository).findByPipeName(pipeName);
        assertTrue(result.isPresent());
    }

    @Test
    public void testUpdatePipe() {
        Long pipeID = 1L;
        PipeEntity existingPipe = new PipeEntity();
        existingPipe.setPipeID(pipeID);
        existingPipe.setPipeName("Old Pipe Name");
        PipeEntity updatedPipe = new PipeEntity();
        updatedPipe.setPipeID(pipeID);
        updatedPipe.setPipeName("New Pipe Name");
        when(pipeRepository.findById(pipeID)).thenReturn(Optional.of(existingPipe));
        when(pipeRepository.save(any(PipeEntity.class))).thenReturn(updatedPipe);
        PipeEntity result = pipeService.updatePipe(pipeID, updatedPipe);
        verify(pipeRepository).findById(pipeID);
        verify(pipeRepository).save(existingPipe);
        assertEquals(updatedPipe, result);
    }

    @Test
    public void testDeletePipes() {
        pipeService.deletePipes();
        verify(pipeRepository).deleteAll();
    }

    @Test
    public void testDeletePipeById() {
        Long pipeID = 1L;
        pipeService.deletePipeById(pipeID);
        verify(pipeRepository).deleteById(pipeID);
    }

    @Test
    public void testExistsPipeById() {
        Long pipeID = 1L;
        when(pipeRepository.findById(pipeID)).thenReturn(Optional.of(new PipeEntity()));
        boolean result = pipeService.existsPipeById(pipeID);
        verify(pipeRepository).findById(pipeID);
        assertTrue(result);
    }
}
