package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.RoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class RoleEntityTest {
    @Test
    public void testRoleEntityAttributes() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleID(Long.valueOf("1"));
        roleEntity.setAccountType("admin");
        assertEquals(Long.valueOf("1"), roleEntity.getRoleID());
        assertEquals("admin", roleEntity.getAccountType());
    }
}
