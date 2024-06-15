package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.CurtainEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class CurtainEntityTest {
    @Mock
    private CurtainEntity curtainsEntityMock;

    @InjectMocks
    private CurtainEntity curtainsEntity = new CurtainEntity(1L, "type");

    @Test
    void testCurtainsEntity() {
        assertEquals(1L, curtainsEntity.getCurtainID());
        assertEquals("type", curtainsEntity.getCurtainType());
    }

    @Test
    void testCurtainsEntity2() {
        assertEquals(1L, curtainsEntity.getCurtainID());
        assertEquals("type", curtainsEntity.getCurtainType());
        CurtainEntity curtainsEntity2 = new CurtainEntity(2L, "type2");
        curtainsEntity2.setCurtainID(1L);
        curtainsEntity2.setCurtainType("type");
        assertEquals(curtainsEntity, curtainsEntity2);
        curtainsEntity.setCurtainType("newType");
        assertEquals("newType", curtainsEntity.getCurtainType());
    }

}
