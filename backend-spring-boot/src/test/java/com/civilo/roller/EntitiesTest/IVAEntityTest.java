package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.IVAEntity;
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
public class IVAEntityTest {
    @Mock
    private IVAEntity ivaEntity;

    @Test
    void testGettersAndSetters() {
        ivaEntity = new IVAEntity();
        ivaEntity.setIvaID(1L);
        ivaEntity.setIvaPercentage(0.21f);

        assertEquals(1L, ivaEntity.getIvaID());
        assertEquals(0.21f, ivaEntity.getIvaPercentage(), 0.001f);
    }

    @Test
    void testAllArgsConstructor() {
        IVAEntity entity = new IVAEntity(2L, 0.19f);

        assertEquals(2L, entity.getIvaID());
        assertEquals(0.19f, entity.getIvaPercentage(), 0.001f);
    }

    @Test
    public void testData() {
        // Crear una instancia de IVAEntity utilizando el constructor sin argumentos
        IVAEntity ivaEntity = new IVAEntity();

        // Establecer valores utilizando los setters generados automáticamente
        ivaEntity.setIvaID(1L);
        ivaEntity.setIvaPercentage(0.19f);

        // Obtener valores utilizando los getters generados automáticamente
        Long ivaID = ivaEntity.getIvaID();
        float ivaPercentage = ivaEntity.getIvaPercentage();

        // Verificar que los valores sean los esperados
        assertEquals(1L, ivaID);
        assertEquals(0.19f, ivaPercentage);
    }
}

