package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.PipeEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PipeEntityTest {
    @Mock
    private PipeEntity pipeEntity;

    @Test
    public void testPipeEntity() {
        pipeEntity = new PipeEntity();

        assertNotNull(pipeEntity);
        assertEquals(null, pipeEntity.getPipeID());
        assertEquals(null, pipeEntity.getPipeName());

        Long expectedPipeID = 1L;
        String expectedPipeName = "Pipe 1";

        pipeEntity.setPipeID(expectedPipeID);
        pipeEntity.setPipeName(expectedPipeName);

        assertEquals(expectedPipeID, pipeEntity.getPipeID());
        assertEquals(expectedPipeName, pipeEntity.getPipeName());
    }
}
