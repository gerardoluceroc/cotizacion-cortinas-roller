package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.*;
import com.civilo.roller.repositories.QuoteRepository;
import com.civilo.roller.repositories.QuoteSummaryRepository;
import com.civilo.roller.services.ProfitMarginService;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class QuoteServiceTest {
    @Mock
    private QuoteRepository quoteRepository;

    @InjectMocks
    private QuoteService quoteService;

    @Mock
    private ProfitMarginService profitMarginService;

    @Test
    public void getQuotesTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role, "companyName", true, "banco", "cuenta", 1);
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", role);
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");
        CoverageEntity coverage = new CoverageEntity(9999L, "Santiago");
        QuoteEntity quoteEntity = new QuoteEntity(1L, 1, 12500f, 1f, 1f, 1f, 12500f, 2300f, 1500f, 900f, 300f, 600f, 190f, 8090f, 2000f, 5000f, 7000f, 0f, 44000f, 77000f, null, null, null, null, null, null, null);

        List<QuoteEntity> quoteList = new ArrayList<>();
        quoteList.add(quoteEntity);
        when(quoteRepository.findAll()).thenReturn(quoteList);

        List<QuoteEntity> result = quoteService.getQuotes();

        verify(quoteRepository, times(1)).findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void getQuoteByIdTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role, "companyName", true, "banco", "cuenta", 1);
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", role);
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");
        CoverageEntity coverage = new CoverageEntity(9999L, "Santiago");
        QuoteEntity quoteEntity = new QuoteEntity(1L, 1, 12500f, 1f, 1f, 1f, 12500f, 2300f, 1500f, 900f, 300f, 600f, 190f, 8090f, 2000f, 5000f, 7000f, 0f, 44000f, 77000f, null, null, null, null, null, null, null);

        when(quoteRepository.findById(anyLong())).thenReturn(Optional.of(quoteEntity));

        Optional<QuoteEntity> result = quoteService.getQuoteById(9999L);

        verify(quoteRepository, times(1)).findById(anyLong());
        assertTrue(result.isPresent());
    }

    @Test
    public void saveQuoteTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role, "companyName", true,  "banco", "cuenta", 1);
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", role);
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");
        CoverageEntity coverage = new CoverageEntity(9999L, "Santiago");
        QuoteEntity quoteEntity = new QuoteEntity(1L, 1, 12500f, 1f, 1f, 1f, 12500f, 2300f, 1500f, 900f, 300f, 600f, 190f, 8090f, 2000f, 5000f, 7000f, 0f, 44000f, 77000f, null, null, null, null, null, null, null);

        when(quoteRepository.save(any(QuoteEntity.class))).thenReturn(quoteEntity);

        QuoteEntity result = quoteService.saveQuote(quoteEntity);

        verify(quoteRepository, times(1)).save(any(QuoteEntity.class));
        assertNotNull(result);
    }

    @Test
    public void createQuoteTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role, "companyName", true, "banco", "cuenta", 1);
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", role);
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");
        CoverageEntity coverage = new CoverageEntity(9999L, "Santiago");
        QuoteEntity quoteEntity = new QuoteEntity(1L, 1, 12500f, 1f, 1f, 1f, 12500f, 2300f, 1500f, 900f, 300f, 600f, 190f, 8090f, 2000f, 5000f, 7000f, 0f, 44000f, 77000f, null, null, null, null, null, null, null);

        when(quoteRepository.save(any(QuoteEntity.class))).thenReturn(quoteEntity);

        QuoteEntity result = quoteService.createQuote(quoteEntity);

        verify(quoteRepository, times(1)).save(any(QuoteEntity.class));
        assertNotNull(result);
    }

    @Test
    public void deleteQuotesTest() {
        quoteService.deleteQuotes();
        verify(quoteRepository, times(1)).deleteAll();
    }

    @Test
    public void deleteQuoteByIdTest() {
        Long id = Long.valueOf("9999");
        quoteService.deleteQuoteById(id);
        verify(quoteRepository, times(1)).deleteById(id);
    }

    @Test
    public void existsQuoteByIdTest() {
        Long id = Long.valueOf("9999");
        when(quoteRepository.findById(id)).thenReturn(Optional.of(new QuoteEntity()));
        boolean result = quoteService.existsQuoteById(id);
        verify(quoteRepository, times(1)).findById(id);
        assertTrue(result);
    }

    @Test
    public void calculation_UpdatesQuoteEntityCorrectly() {
        QuoteEntity quote = new QuoteEntity();
        quote.setHeight(1f);
        quote.setWidth(1f);
        quote.setValueSquareMeters(12500f);
        quote.setAmount(1);
        quote.setBracketValue(2500f);
        quote.setCapValue(1400f);
        quote.setPipeValue(2300f);
        quote.setCounterweightValue(1400f);
        quote.setBandValue(300f);
        quote.setChainValue(190f);
        quote.setAssemblyValue(2000f);
        quote.setInstallationValue(5000f);
        ProfitMarginEntity profitMarginEntity = new ProfitMarginEntity(1L, 40f, 0.4f);
        quote.setProfitMarginEntity(profitMarginEntity);
        when(profitMarginService.getLastProfitMargin()).thenReturn(profitMarginEntity);

        quoteService.calculation(quote);

        assertNotNull(quote.getDate());
        assertEquals(1f, quote.getTotalSquareMeters());
        assertEquals(12500f, quote.getTotalFabrics());
        assertEquals(8090f, quote.getTotalMaterials());
        assertEquals(7000f, quote.getTotalLabor());
        assertEquals(27590f, quote.getProductionCost());
        assertEquals(45984f, quote.getSaleValue());
    }

    @Test
    public void createQuotes_SavesQuoteEntitiesToRepository() {
        List<QuoteEntity> quoteList = new ArrayList<>();
        quoteList.add(new QuoteEntity());
        quoteList.add(new QuoteEntity());
        quoteList.add(new QuoteEntity());
        quoteService.createQuotes(quoteList);
        Mockito.verify(quoteRepository, times(3)).save(Mockito.any(QuoteEntity.class));
    }

    @Test
    public void testInstalation() {
        QuoteEntity quote = new QuoteEntity();
        quote.setHeight(1f);
        quote.setWidth(1f);
        quote.setValueSquareMeters(12500f);
        quote.setAmount(1);
        quote.setBracketValue(2500f);
        quote.setCapValue(1400f);
        quote.setPipeValue(2300f);
        quote.setCounterweightValue(1400f);
        quote.setBandValue(300f);
        quote.setChainValue(190f);
        quote.setAssemblyValue(2000f);
        quote.setInstallationValue(5000f);
        ProfitMarginEntity profitMarginEntity = new ProfitMarginEntity(1L, 40f, 0.4f);
        quote.setProfitMarginEntity(profitMarginEntity);
        List<QuoteEntity> quoteList = new ArrayList<>();
        quoteList.add(quote);

        String expectedResult = "Si";
        String result = quoteService.instalation(quoteList);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testInstalation2() {
        QuoteEntity quote = new QuoteEntity();
        quote.setHeight(1f);
        quote.setWidth(1f);
        quote.setValueSquareMeters(12500f);
        quote.setAmount(1);
        quote.setBracketValue(2500f);
        quote.setCapValue(1400f);
        quote.setPipeValue(2300f);
        quote.setCounterweightValue(1400f);
        quote.setBandValue(300f);
        quote.setChainValue(190f);
        quote.setAssemblyValue(0f);
        quote.setInstallationValue(0f);
        ProfitMarginEntity profitMarginEntity = new ProfitMarginEntity(1L, 40f, 0.4f);
        quote.setProfitMarginEntity(profitMarginEntity);
        List<QuoteEntity> quoteList = new ArrayList<>();
        quoteList.add(quote);

        String expectedResult = "No";
        String result = quoteService.instalation(quoteList);
        assertEquals(expectedResult, result);
    }

    @Mock
    private QuoteSummaryRepository quoteSummaryRepository;

    @InjectMocks
    private QuoteSummaryService quoteSummaryService;


/*
    @Test
    public void testLastQuoteSummary2() {
        Long idseller = 1L;
        List<QuoteSummaryEntity> quoteSummaryEntities = new ArrayList<>();
        SellerEntity seller = new SellerEntity(1L, null, null, null, null, null, null, null, 0, null, null, null, null, true, null, null, null, 0);
        quoteSummaryEntities.add(new QuoteSummaryEntity(1L, "a", 1f, 2f, 3f, 4f, 0f, 5f, new Date(), seller, null)); // Add some quote summary entities for testing
        when(quoteSummaryRepository.findAll()).thenReturn(quoteSummaryEntities);
        QuoteSummaryEntity result = quoteService.lastQuoteSummary(idseller);
        assertEquals(quoteSummaryEntities.get(quoteSummaryEntities.size() - 1), result);
        verify(quoteSummaryRepository, times(1)).findAll();
    }

 */

    @Test
    void testListQuoteSummary() {
        Long idSeller = 1L;
        List<QuoteSummaryEntity> quoteSummaryEntities = new ArrayList<>();
        quoteSummaryEntities.add(new QuoteSummaryEntity(1L, null, 0, 0, 0, 0, 0, 0, null, new SellerEntity(1L, null, null, null, null, null, null, null, null, 0, null, null, null, null, true, null, null, 0), null));
        quoteSummaryEntities.add(new QuoteSummaryEntity());
        quoteSummaryEntities.add(new QuoteSummaryEntity());
        when(quoteSummaryRepository.findAll()).thenReturn(quoteSummaryEntities);
        List<QuoteSummaryEntity> quoteSummarySelected = quoteService.listQuoteSummary(idSeller);
        verify(quoteSummaryRepository, times(1)).findAll();
        assertEquals(1, quoteSummarySelected.size());
    }

    @Test
    void testFindQuoteSummary() {
        Long idQuoteSelected = 1L;
        Long idSeller = 1L;
        SellerEntity seller = new SellerEntity(1L, null, null, null, null, null, null, null, null, 0, null, null, null, null, true, null, null, 0);
        List<QuoteSummaryEntity> listSummary = new ArrayList<>();
        QuoteSummaryEntity quoteSummary1 = new QuoteSummaryEntity(1L, null, 0, 0, 0, 0, 0, 0, null, new SellerEntity(1L, null, null, null, null, null, null, null, null, 0, null, null, null, null, true, null, null, 0), null);
        quoteSummary1.setSeller(seller);
        listSummary.add(quoteSummary1);
        QuoteEntity quoteEntity1 = new QuoteEntity(1L, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, null, null, null, null, null);
        quoteEntity1.setQuoteSummary(quoteSummary1);
        quoteEntity1.setSeller(seller);
        List<QuoteEntity> quoteEntities = new ArrayList<>();
        quoteEntity1.setRequestEntity(new RequestEntity(1L, null, null, null, null, null, 1, null, null, null, null, null));
        quoteEntity1.getSeller().setUserID(idSeller);
        quoteEntities.add(quoteEntity1);
        QuoteSummaryEntity expectedQuoteSummary = quoteSummary1;
        when(quoteRepository.findAll()).thenReturn(quoteEntities);
        QuoteSummaryEntity actualQuoteSummary = quoteService.findQuoteSummary(listSummary, idQuoteSelected, idSeller);
        verify(quoteRepository, times(1)).findAll();
        assertEquals(expectedQuoteSummary, actualQuoteSummary);
    }

    @Test
    void testFindQuoteSummary2() {
        Long idQuoteSelected = 2L;
        Long idSeller = 1L;
        SellerEntity seller = new SellerEntity(1L, null, null, null, null, null, null, null, null, 0, null, null, null, null, true, null, null, 0);
        List<QuoteSummaryEntity> listSummary = new ArrayList<>();
        QuoteSummaryEntity quoteSummary1 = new QuoteSummaryEntity(1L, null, 0, 0, 0, 0, 0, 0, null, new SellerEntity(1L, null, null, null, null, null, null, null, null, 0, null, null, null, null, true, null, null, 0), null);
        quoteSummary1.setSeller(seller);
        listSummary.add(quoteSummary1);
        QuoteEntity quoteEntity1 = new QuoteEntity(1L, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, null, null, null, null, null);
        quoteEntity1.setQuoteSummary(quoteSummary1);
        quoteEntity1.setSeller(seller);
        List<QuoteEntity> quoteEntities = new ArrayList<>();
        quoteEntity1.setRequestEntity(new RequestEntity(1L, null, null, null, null, null, 1, null, null, null, null, null));
        quoteEntity1.getSeller().setUserID(idSeller);
        quoteEntities.add(quoteEntity1);
        QuoteSummaryEntity expectedQuoteSummary = quoteSummary1;
        when(quoteRepository.findAll()).thenReturn(quoteEntities);
        QuoteSummaryEntity actualQuoteSummary = quoteService.findQuoteSummary(listSummary, idQuoteSelected, idSeller);
        verify(quoteRepository, times(1)).findAll();
        assertEquals(new QuoteSummaryEntity(), actualQuoteSummary);
    }

    @Test
    void testListQuotes() {
        Long idQuoteSelected = 1L;
        Long idSeller = 1L;
        SellerEntity seller = new SellerEntity(1L, null, null, null, null, null, null, null, null, 0, null, null, null, null, true, null, null, 0);
        QuoteSummaryEntity summarySelected = new QuoteSummaryEntity(1L, null, 0, 0, 0, 0, 0, 0, null, seller, null);
        List<QuoteEntity> quoteEntities = new ArrayList<>();
        QuoteEntity quoteEntity1 = new QuoteEntity(1L, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, seller, null, null, null, null, null);
        quoteEntity1.setQuoteSummary(summarySelected);
        quoteEntity1.setRequestEntity(new RequestEntity(1L, null, null, null, null, null, 1, null, null, null, null, null));
        quoteEntity1.getSeller().setUserID(idSeller);
        quoteEntities.add(quoteEntity1);
        List<QuoteEntity> expectedQuoteEntities = new ArrayList<>();
        expectedQuoteEntities.add(quoteEntity1);
        when(quoteRepository.findAll()).thenReturn(quoteEntities);
        List<QuoteEntity> actualQuoteEntities = quoteService.listQuotes(summarySelected, idQuoteSelected, idSeller);
        verify(quoteRepository, times(1)).findAll();
        assertEquals(expectedQuoteEntities, actualQuoteEntities);
    }

    @Test
    public void testExistQuoteSummaryWithMyInfo_emptyList() {
        List<QuoteEntity> quoteEntities = new ArrayList<>();
        Long result = quoteService.existQuoteSummaryWithMyInfo(quoteEntities);
        assertNull(result);
    }

    @Test
    public void testExistQuoteSummaryWithMyInfo_ExistingSummary() {
        // Crear una lista de citas
        List<QuoteEntity> quoteEntities = new ArrayList<>();

        // Crear una cita con el mismo ID de solicitud que la cita existente
        QuoteSummaryEntity summarySelected = new QuoteSummaryEntity(1L, null, 0, 0, 0, 0, 0, 0, null, null, null);
        RequestEntity requestEntity = new RequestEntity(1L, null, null, null, null, null, 1, null, null, null, null, null);
        QuoteEntity quoteEntity = new QuoteEntity(1L, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, null, null, summarySelected, null, null);
        quoteEntity.setRequestEntity(requestEntity);
        quoteEntities.add(quoteEntity);

        // Llamar a la función existQuoteSummaryWithMyInfo
        when(quoteService.getQuotes()).thenReturn(quoteEntities);
        Long quoteSummaryID = quoteService.existQuoteSummaryWithMyInfo(quoteEntities);

        // Comprobar que se devuelve el ID de resumen de cita correcto
        assertEquals(1L, quoteSummaryID.longValue());
    }

    @Test
    public void testUpdateQuotesWithMyInfo() {
        // Crear una lista de citas existentes
        List<QuoteEntity> quoteEntityList = new ArrayList<>();

        // Crear una cita existente
        RequestEntity existingRequestEntity = new RequestEntity();
        existingRequestEntity.setRequestID(1L); // ID de solicitud existente
        QuoteEntity existingQuoteEntity = new QuoteEntity();
        existingQuoteEntity.setRequestEntity(existingRequestEntity);
        existingQuoteEntity.setQuoteID(1L); // ID de cita existente
        quoteEntityList.add(existingQuoteEntity);

        // Crear una lista de citas para actualizar
        List<QuoteEntity> quoteEntities = new ArrayList<>();

        // Crear una cita con el mismo ID de solicitud que la cita existente
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestID(1L); // ID de solicitud existente
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setRequestEntity(requestEntity);
        quoteEntities.add(quoteEntity);

        // Llamar a la función updateQuotesWithMyInfo
        when(quoteService.getQuotes()).thenReturn(quoteEntities);
        quoteService.updateQuotesWithMyInfo(quoteEntities);

        // Comprobar que la cita existente se actualizó con el nuevo ID
        assertEquals(1L, existingQuoteEntity.getQuoteID().longValue());
    }



}
