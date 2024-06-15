package com.civilo.roller.ServiceTest;


import com.civilo.roller.Entities.IVAEntity;
import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.Entities.QuoteSummaryEntity;
import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.repositories.IVARepository;
import com.civilo.roller.repositories.QuoteSummaryRepository;
import com.civilo.roller.services.IVAService;
import com.civilo.roller.services.QuoteSummaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class QuoteSummaryServiceTest {
    @Mock
    private QuoteSummaryRepository quoteSummaryRepository;

    @InjectMocks
    private QuoteSummaryService quoteSummaryService;

    @MockBean
    private IVAService ivaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        IVAEntity ivaEntity = new IVAEntity();
        ivaEntity.setIvaPercentage(16);
        when(ivaService.getLastIVA()).thenReturn(ivaEntity);
    }


    @Test
    public void testSaveQuoteSummary() {
        QuoteSummaryEntity quoteSummary = new QuoteSummaryEntity();
        when(quoteSummaryRepository.save(quoteSummary)).thenReturn(quoteSummary);
        QuoteSummaryEntity result = quoteSummaryService.saveQuoteSummary(quoteSummary);
        assertEquals(quoteSummary, result);
    }

    @Test
    public void testSummaryCalculation() {
        List<QuoteEntity> quoteEntities = new ArrayList<>();
        QuoteEntity quote1 = new QuoteEntity(1L, 1, 12500f, 1f, 1f, 1f, 12500f, 2300f, 1500f, 900f, 300f, 600f, 190f, 8090f, 2000f, 5000f, 7000f, 0f, 44000f, 77000f, new Date(), null, null, null, null, null, null);
        quoteEntities.add(quote1);
        IVAEntity ivaEntity = new IVAEntity(1L, 19f);
        when(ivaService.getLastIVA()).thenReturn(ivaEntity);
        QuoteSummaryEntity quoteSummary = new QuoteSummaryEntity();
        quoteSummary = quoteSummaryService.summaryCalculation(quoteEntities, 1L);

        List<QuoteEntity> quoteEntities2 = new ArrayList<>();
        QuoteEntity quote2 = new QuoteEntity(1L, 1, 12500f, 1f, 1f, 1f, 12500f, 2300f, 1500f, 900f, 300f, 600f, 190f, 8090f, 2000f, 5000f, 7000f, 1f, 44000f, 77000f, new Date(), null, null, null, null, null, null);
        QuoteSummaryEntity quoteSummary2 = new QuoteSummaryEntity();
        quoteEntities2.add(quote2);
        quoteSummary2 = quoteSummaryService.summaryCalculation(quoteEntities2, 1L);


    }


}
