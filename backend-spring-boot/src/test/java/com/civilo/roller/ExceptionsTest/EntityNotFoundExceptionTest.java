package com.civilo.roller.ExceptionsTest;

import com.civilo.roller.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityNotFoundExceptionTest {
    @Test
    public void testConstructor() {
        String message = "Entity not found";
        EntityNotFoundException exception = new EntityNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }
}
