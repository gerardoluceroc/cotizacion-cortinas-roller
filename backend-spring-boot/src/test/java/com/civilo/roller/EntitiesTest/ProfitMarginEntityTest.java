package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.ProfitMarginEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfitMarginEntityTest {
    @Test
    public void testProfitMarginEntity() {
        // Crear una instancia de ProfitMarginEntity
        ProfitMarginEntity profitMargin = new ProfitMarginEntity();
        profitMargin.setProfitMarginID(1L);
        profitMargin.setProfitMarginPercentaje(40f);
        profitMargin.setDecimalProfitMargin(0.4f);

        // Verificar los valores de los atributos
        assertEquals(1L, profitMargin.getProfitMarginID());
        assertEquals(40f, profitMargin.getProfitMarginPercentaje());
        assertEquals(0.4f, profitMargin.getDecimalProfitMargin());
    }
}
