package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.*;
import com.civilo.roller.services.RequestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class QuoteEntityTest {
    @InjectMocks
    private QuoteEntity quoteEntity;

    @Test
    public void quoteEntityTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", role);
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role, "companyName", true,  "banco", "cuenta", 1);
        CurtainEntity curtain = new CurtainEntity(Long.valueOf("9999"), "Curtain 1");
        quoteEntity.setQuoteID(Long.valueOf("9999"));
        quoteEntity.setAmount(1);
        quoteEntity.setValueSquareMeters(10.0f);
        quoteEntity.setWidth(5.0f);
        quoteEntity.setHeight(2.5f);
        quoteEntity.setTotalSquareMeters(12.5f);
        quoteEntity.setTotalFabrics(15.0f);
        quoteEntity.setBracketValue(1.0f);
        quoteEntity.setCapValue(2.0f);
        quoteEntity.setPipeValue(0.5f);
        quoteEntity.setCounterweightValue(0.5f);
        quoteEntity.setBandValue(0.2f);
        quoteEntity.setChainValue(0.3f);
        quoteEntity.setTotalMaterials(4.0f);
        quoteEntity.setAssemblyValue(10.0f);
        quoteEntity.setInstallationValue(5.0f);
        quoteEntity.setTotalLabor(15.0f);
        quoteEntity.setProductionCost(100.0f);
        quoteEntity.setSaleValue(150.0f);
        quoteEntity.setPercentageDiscount(0.1f);
        quoteEntity.setSeller(seller);
        quoteEntity.setCurtain(curtain);
        quoteEntity.setProfitMarginEntity(new ProfitMarginEntity(1L, 40f, 0.4f));
        Date dateFake = new Date();
        dateFake.setYear(122);
        dateFake.setMonth(5);
        dateFake.setDate(1);
        dateFake.setHours(10);
        dateFake.setMinutes(30);
        dateFake.setSeconds(0);
        quoteEntity.setDate(dateFake);
        quoteEntity.setPipe(new PipeEntity(1L, "tubo 10 mm"));
        quoteEntity.setQuoteSummary(new QuoteSummaryEntity());
        assertEquals(Long.valueOf("9999"), quoteEntity.getQuoteID());
        assertEquals(1, quoteEntity.getAmount());
        assertEquals(10.0f, quoteEntity.getValueSquareMeters());
        assertEquals(5.0f, quoteEntity.getWidth());
        assertEquals(2.5f, quoteEntity.getHeight());
        assertEquals(12.5f, quoteEntity.getTotalSquareMeters());
        assertEquals(15.0f, quoteEntity.getTotalFabrics());
        assertEquals(1.0f, quoteEntity.getBracketValue());
        assertEquals(2.0f, quoteEntity.getCapValue());
        assertEquals(0.5f, quoteEntity.getPipeValue());
        assertEquals(0.5f, quoteEntity.getCounterweightValue());
        assertEquals(0.2f, quoteEntity.getBandValue());
        assertEquals(0.3f, quoteEntity.getChainValue());
        assertEquals(4.0f, quoteEntity.getTotalMaterials());
        assertEquals(10.0f, quoteEntity.getAssemblyValue());
        assertEquals(5.0f, quoteEntity.getInstallationValue());
        assertEquals(15.0f, quoteEntity.getTotalLabor());
        assertEquals(100.0f, quoteEntity.getProductionCost());
        assertEquals(150.0f, quoteEntity.getSaleValue());
        assertEquals(0.1f, quoteEntity.getPercentageDiscount());
        assertEquals(dateFake, quoteEntity.getDate());
        assertEquals(seller, quoteEntity.getSeller());
        assertEquals(curtain, quoteEntity.getCurtain());
        assertEquals(new PipeEntity(1L, "tubo 10 mm"), quoteEntity.getPipe());
        assertEquals(new QuoteSummaryEntity(), quoteEntity.getQuoteSummary());
        assertEquals(new ProfitMarginEntity(1L, 40f, 0.4f), quoteEntity.getProfitMarginEntity());
    }

    @Test
    public void testDataMethods() {
        IVAEntity ivaEntity = new IVAEntity();
        Long ivaID = 1L;
        float ivaPercentage = 0.15f;
        ivaEntity.setIvaID(ivaID);
        ivaEntity.setIvaPercentage(ivaPercentage);
        Assertions.assertEquals(ivaID, ivaEntity.getIvaID(), "Error en el método getIvaID()");
        Assertions.assertEquals(ivaPercentage, ivaEntity.getIvaPercentage(), "Error en el método getIvaPercentage()");
        IVAEntity anotherIvaEntity = new IVAEntity();
        anotherIvaEntity.setIvaID(ivaID);
        anotherIvaEntity.setIvaPercentage(ivaPercentage);
        Assertions.assertEquals(ivaEntity, anotherIvaEntity, "Error en el método equals()");
        Assertions.assertEquals(ivaEntity.hashCode(), anotherIvaEntity.hashCode(), "Error en el método hashCode()");
        String expectedToString = "IVAEntity(ivaID=" + ivaID + ", ivaPercentage=" + ivaPercentage + ")";
        Assertions.assertEquals(expectedToString, ivaEntity.toString(), "Error en el método toString()");
    }


}