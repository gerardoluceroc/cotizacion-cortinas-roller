package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.StatusEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class StatusEntityTest {
    @Test
    public void testStatusEntity() {
        StatusEntity status = new StatusEntity();
        status.setStatusID(1L);
        status.setStatusName("Active");
        assertEquals(1L, status.getStatusID());
        assertEquals("Active", status.getStatusName());
    }
}