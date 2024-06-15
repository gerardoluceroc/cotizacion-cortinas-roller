package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.*;
import com.civilo.roller.controllers.QuoteController;
import com.civilo.roller.repositories.QuoteRepository;
import com.civilo.roller.services.QuoteService;
import com.civilo.roller.services.QuoteSummaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class QuoteControllerTest {
    @Mock
    private QuoteService quoteService;
    @InjectMocks
    private QuoteController quoteController;

    @Mock
    private QuoteRepository quoteRepository;

    @Mock
    private QuoteSummaryService quoteSummaryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getQuotes() {
        List<QuoteEntity> expectedQuotes = new ArrayList<>();
        expectedQuotes.add(new QuoteEntity(1L, 1, 12500f, 1f, 1f, 1f, 12500f, 2300f, 1500f, 900f, 300f, 600f, 190f, 8090f, 2000f, 5000f, 7000f, 0f, 44000f, 77000f, null, null, null, null, null, null, null));
        expectedQuotes.add(new QuoteEntity(2L, 1, 10.0f, 5.0f, 5.0f, 25.0f, 20.0f, 5.0f, 2.0f, 3.0f, 1.0f, 0.5f, 2.5f, 15.0f, 8.0f, 7.0f, 30.0f, 50.0f, 10.0f, 77000f, null, null, null, null, null, null, null));
        Mockito.when(quoteService.getQuotes()).thenReturn(expectedQuotes);
        List<QuoteEntity> actualQuotes = quoteController.getQuotes();
        assertEquals(expectedQuotes, actualQuotes);
    }

    @Test
    public void testDeleteQuote() {
        quoteService.deleteQuotes();
        verify(quoteRepository, times(0)).deleteAll();
    }

    @Test
    public void testGeneratePDF1() {
        Long id = 1L;
        SellerEntity seller = new SellerEntity(1L, null, null, null, null, null, null, null, null, 0, null, null, null, null, true, null, null, 0);

        List<QuoteSummaryEntity> listSummary = new ArrayList<>();
        QuoteSummaryEntity summarySelected = new QuoteSummaryEntity(2L, null, 0, 0, 0, 0, 0, 0, null, seller, null);
        listSummary.add(summarySelected);
        List<QuoteEntity> listQuotes = new ArrayList<>();
        listQuotes.add(new QuoteEntity(1L, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, seller, null, null, null, null, null));
        when(quoteService.listQuoteSummary(anyLong())).thenReturn(listSummary);
        when(quoteService.findQuoteSummary(anyList(), anyLong(), anyLong())).thenReturn(new QuoteSummaryEntity());
        when(quoteService.listQuotes(any(QuoteSummaryEntity.class), anyLong(), anyLong())).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = quoteController.generatePDF(id, seller);

        assertEquals("No se encontro un resumen de cotizacion para la solicitud seleccionada.", response.getBody());
    }

    @Test
    public void testGeneratePDF2() {
        Long id = 1L;
        SellerEntity seller = new SellerEntity(1L, null, null, null, null, null, null, null, null, 0, null, null, null, null, true, null, null, 0);

        List<QuoteSummaryEntity> listSummary = new ArrayList<>();
        QuoteSummaryEntity summarySelected = new QuoteSummaryEntity(2L, null, 0, 0, 0, 0, 0, 0, null, seller, null);
        listSummary.add(summarySelected);
        List<QuoteEntity> listQuotes = new ArrayList<>();
        listQuotes.add(new QuoteEntity(1L, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, seller, null, null, null, null, null));
        when(quoteService.listQuoteSummary(anyLong())).thenReturn(listSummary);
        when(quoteService.findQuoteSummary(anyList(), anyLong(), anyLong())).thenReturn(summarySelected);
        when(quoteService.listQuotes(any(QuoteSummaryEntity.class), anyLong(), anyLong())).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = quoteController.generatePDF(id, seller);

        assertEquals("No se encontraron cotizaciones para la solicitud seleccionada.", response.getBody());
    }

    @Test
    public void testGeneratePDF3() {
        Long id = 1L;
        SellerEntity seller = new SellerEntity(1L, "Name", null, null, null, null, null, "Comuna", null, 0, null, null, null, null, true, null, null, 0);

        List<QuoteSummaryEntity> listSummary = new ArrayList<>();
        QuoteSummaryEntity summarySelected = new QuoteSummaryEntity(2L, null, 0, 0, 0, 0, 0, 0, null, seller, new IVAEntity(1L, 19f));
        listSummary.add(summarySelected);

        List<QuoteEntity> listQuotes = new ArrayList<>();
        RequestEntity request = new RequestEntity(1L, null, null, null, null, null, 1, null, seller, new CoverageEntity(1L, "Comuna"), new CurtainEntity(1L, "Cortina"), null);
        listQuotes.add(new QuoteEntity(1L, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, seller, new CurtainEntity(1L, "Cortina"), null, null, new ProfitMarginEntity(1L, 40f, 0.4f), request));

        when(quoteService.listQuoteSummary(anyLong())).thenReturn(listSummary);
        when(quoteService.findQuoteSummary(anyList(), anyLong(), anyLong())).thenReturn(summarySelected);
        when(quoteService.listQuotes(any(QuoteSummaryEntity.class), anyLong(), anyLong())).thenReturn(listQuotes);

        ResponseEntity<?> response = quoteController.generatePDF(id, seller);

        assertNotNull(response.getBody());
    }

    @Test
    public void testSaveQuotes() {
        List<QuoteEntity> quoteList = new ArrayList<>();
        // Add some quote entities to the quoteList for testing
        quoteList.add(new QuoteEntity());

        String description = "Test Description";

        QuoteSummaryEntity quoteSummary = new QuoteSummaryEntity();
        quoteSummary.setTotalCostOfProduction(100);
        quoteSummary.setTotalSaleValue(200);
        quoteSummary.setValueAfterDiscount(180);
        quoteSummary.setNetTotal(160);
        quoteSummary.setTotal(192);

        when(quoteService.existQuoteSummaryWithMyInfo(quoteList)).thenReturn(1L);
        when(quoteSummaryService.summaryCalculation(quoteList, 1L)).thenReturn(quoteSummary);

        ResponseEntity<QuoteSummaryEntity> result = quoteController.saveQuotes(quoteList, description);

        // Assert that the response entity is not null
        assertEquals(HttpStatus.OK, result.getStatusCode());

        // Assert that the quote summary in the response entity matches the expected quote summary
        assertEquals(quoteSummary, result.getBody());

        // Verify that the quoteService and quoteSummaryService methods were called correctly
        verify(quoteService, times(1)).existQuoteSummaryWithMyInfo(quoteList);
        verify(quoteSummaryService, times(1)).summaryCalculation(quoteList, 1L);
        verify(quoteService, times(1)).updateQuotesWithMyInfo(quoteList);
    }

    @Test
    public void testDeleteQuotes() {
        // Arrange & Act
        ResponseEntity<String> response = quoteController.deleteQuotes();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SE ELIMINARON LAS COTIZACIONES CORRECTAMENTE", response.getBody());
        verify(quoteService, times(1)).deleteQuotes();
    }

    @Test
    public void testDeleteQuoteById_ExistingId_ReturnsOkResponse() {
        // Arrange
        Long id = 1L;
        when(quoteService.existsQuoteById(id)).thenReturn(true);

        // Act
        ResponseEntity<String> response = quoteController.deleteQuoteById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("COTIZACION CON ID " + id + " ELIMINADA CORRECTAMENTE \n", response.getBody());
        verify(quoteService, times(1)).deleteQuoteById(id);
    }

    @Test
    public void testDeleteQuoteById_NonExistingId_ReturnsNotFoundResponse() {
        // Arrange
        Long id = 1L;
        when(quoteService.existsQuoteById(id)).thenReturn(false);

        // Act
        ResponseEntity<String> response = quoteController.deleteQuoteById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(quoteService, never()).deleteQuoteById(id);
    }

    @Test
    public void testGetQuoteById_ExistingId_ReturnsQuoteEntity() {
        // Arrange
        Long id = 1L;
        QuoteEntity expectedQuote = new QuoteEntity();
        when(quoteService.getQuoteById(id)).thenReturn(Optional.of(expectedQuote));

        // Act
        ResponseEntity<QuoteEntity> response = quoteController.getQuoteById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedQuote, response.getBody());
    }

    @Test
    public void testGetQuoteById_NonExistingId_ReturnsNotFoundResponse() {
        // Arrange
        Long id = 1L;
        when(quoteService.getQuoteById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<QuoteEntity> response = quoteController.getQuoteById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetQuoteSellerId_ReturnsListOfQuotes() {
        // Arrange
        Long sellerId = 1L;
        List<QuoteEntity> expectedQuotes = new ArrayList<>();
        when(quoteService.sellerQuotes(sellerId)).thenReturn(expectedQuotes);

        // Act
        List<QuoteEntity> result = quoteController.getQuoteSellerId(sellerId);

        // Assert
        assertEquals(expectedQuotes, result);
    }
}
