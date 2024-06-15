package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.DataTransferObjectEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class DataTransferObjectTest {
    @Mock
    private DataTransferObjectEntity dataTransferObjectEntity;

    @Test
    public void testEmailAndPassword() {
        String email = "test@test.com";
        String password = "testpassword";
        DataTransferObjectEntity dto = new DataTransferObjectEntity();
        dto.setEmail(email);
        dto.setPassword(password);
        assertEquals(dto.getEmail(), email);
        assertEquals(dto.getPassword(), password);
    }
}
