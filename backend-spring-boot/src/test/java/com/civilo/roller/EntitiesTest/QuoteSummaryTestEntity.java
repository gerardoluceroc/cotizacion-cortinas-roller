package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.IVAEntity;
import com.civilo.roller.Entities.QuoteSummaryEntity;
import com.civilo.roller.Entities.SellerEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class QuoteSummaryTestEntity {
    @Test
    public void testGettersAndSetters() {
        QuoteSummaryEntity quoteSummary = new QuoteSummaryEntity();

        // Set values using setters
        quoteSummary.setQuoteSummaryID(1L);
        quoteSummary.setDescription("Test description");
        quoteSummary.setTotalCostOfProduction(100.0f);
        quoteSummary.setTotalSaleValue(200.0f);
        quoteSummary.setValueAfterDiscount(180.0f);
        quoteSummary.setNetTotal(150.0f);
        quoteSummary.setPercentageDiscount(10.0f);
        quoteSummary.setTotal(165.0f);
        quoteSummary.setDate(new Date());

        // Set related entities
        SellerEntity seller = new SellerEntity();
        seller.setUserID(1L);
        quoteSummary.setSeller(seller);

        IVAEntity iva = new IVAEntity();
        iva.setIvaID(1L);
        quoteSummary.setCurrentIVA(iva);

        // Verify values using getters
        Assertions.assertEquals(1L, quoteSummary.getQuoteSummaryID());
        Assertions.assertEquals("Test description", quoteSummary.getDescription());
        Assertions.assertEquals(100.0f, quoteSummary.getTotalCostOfProduction(), 0.01);
        Assertions.assertEquals(200.0f, quoteSummary.getTotalSaleValue(), 0.01);
        Assertions.assertEquals(180.0f, quoteSummary.getValueAfterDiscount(), 0.01);
        Assertions.assertEquals(150.0f, quoteSummary.getNetTotal(), 0.01);
        Assertions.assertEquals(10.0f, quoteSummary.getPercentageDiscount(), 0.01);
        Assertions.assertEquals(165.0f, quoteSummary.getTotal(), 0.01);
        Assertions.assertNotNull(quoteSummary.getDate());
        Assertions.assertEquals(1L, quoteSummary.getSeller().getUserID());
        Assertions.assertEquals(1L, quoteSummary.getCurrentIVA().getIvaID());
    }

    @Test
    public void testGettersAndSetters2() {
        QuoteSummaryEntity quoteSummary = new QuoteSummaryEntity(
                1L,
                "Test description",
                100.0f,
                200.0f,
                180.0f,
                150.0f,
                10.0f,
                165.0f,
                new Date(),
                new SellerEntity(),
                new IVAEntity()
        );

        // Verify values using getters
        Assertions.assertEquals(1L, quoteSummary.getQuoteSummaryID());
        Assertions.assertEquals("Test description", quoteSummary.getDescription());
        Assertions.assertEquals(100.0f, quoteSummary.getTotalCostOfProduction(), 0.01);
        Assertions.assertEquals(200.0f, quoteSummary.getTotalSaleValue(), 0.01);
        Assertions.assertEquals(180.0f, quoteSummary.getValueAfterDiscount(), 0.01);
        Assertions.assertEquals(150.0f, quoteSummary.getNetTotal(), 0.01);
        Assertions.assertEquals(10.0f, quoteSummary.getPercentageDiscount(), 0.01);
        Assertions.assertEquals(165.0f, quoteSummary.getTotal(), 0.01);
        Assertions.assertNotNull(quoteSummary.getDate());
        Assertions.assertEquals(new SellerEntity(), quoteSummary.getSeller());
        Assertions.assertEquals(new IVAEntity(), quoteSummary.getCurrentIVA());
    }
}
