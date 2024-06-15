package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.PermissionEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class PermissionEntityTest {
    @Test
    void testPermissionEntity() {
        PermissionEntity permission = new PermissionEntity();
        Long permissionID = 1L;
        permission.setPermissionID(permissionID);
        assertEquals(permissionID, permission.getPermissionID());
    }
}
