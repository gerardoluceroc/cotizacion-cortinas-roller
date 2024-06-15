package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.CoverageEntity;
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
public class CoverageEntityTest {
    @Mock
    private CoverageEntity coverage;

    @Test
    public void entity() {
        Long coverageID = Long.valueOf("9999");
        String commune = "Maipu";
        CoverageEntity coverageEntity = new CoverageEntity();
        coverageEntity.setCoverageID(coverageID);
        coverageEntity.setCommune(commune);
        assertEquals(coverageEntity.getCoverageID(), coverageID);
        assertEquals(coverageEntity.getCommune(), commune);
    }
}
