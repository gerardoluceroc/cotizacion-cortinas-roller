package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.CurtainEntity;
import com.civilo.roller.Entities.IVAEntity;
import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.Entities.QuoteSummaryEntity;
import com.civilo.roller.controllers.QuoteController;
import com.civilo.roller.repositories.QuoteRepository;
import com.civilo.roller.services.IVAService;
import com.civilo.roller.services.QuoteService;
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
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class QuoteSummaryControllerTest {
    @Mock
    private QuoteService quoteService;

    @Mock
    private QuoteSummaryService quoteSummaryService;

    @InjectMocks
    private QuoteController quoteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*
    @Test
    public void testSaveQuotes() {
        List<QuoteEntity> quoteList = new ArrayList<>();
        QuoteEntity quote1 = new QuoteEntity(1L, 1, 12500f, 1f, 1f, 1f, 12500f, 2300f, 1500f, 900f, 300f, 600f, 190f, 8090f, 2000f, 5000f, 7000f, 0f, 44000f, 77000f, new Date(), null, new CurtainEntity(1L, "cor 1"), null, null, null, null);
        QuoteEntity quote2 = new QuoteEntity(1L, 0, 12500f, 1f, 1f, 1f, 12500f, 2300f, 1500f, 900f, 300f, 600f, 190f, 8090f, 2000f, 5000f, 7000f, 0f, 44000f, 77000f, new Date(), null, new CurtainEntity(2L, "cor 2"), null, null, null, null);
        quoteList.add(quote1);
        quoteList.add(quote2);
        QuoteSummaryEntity quoteSummary = new QuoteSummaryEntity();
        when(quoteSummaryService.summaryCalculation(quoteList, anyLong())).thenReturn(quoteSummary);
        quoteController.saveQuotes(quoteList);
    }

     */
}
