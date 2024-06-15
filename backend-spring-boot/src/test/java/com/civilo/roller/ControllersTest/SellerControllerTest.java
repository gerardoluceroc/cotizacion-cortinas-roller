package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.CoverageEntity;
import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.controllers.SellerController;
import com.civilo.roller.services.SellerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class SellerControllerTest {
    @Mock
    private SellerService sellerService;
    @InjectMocks
    private SellerController sellerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Test
    void testGetSellers() {
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        List<SellerEntity> expectedSellers = new ArrayList<>();
        expectedSellers.add(new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, new RoleEntity(1L, "Cliente"), "Company", true, "Banco 1", "Tipo de cuenta", 1));
        expectedSellers.add(new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, new RoleEntity(1L, "Cliente"), "Company", true, "Banco 1", "Tipo de cuenta", 2));
        when(sellerService.getSellers()).thenReturn(expectedSellers);
        List<SellerEntity> actualSeller = sellerController.getSellers();
        assertEquals(expectedSellers, actualSeller);
    }

    @Test
    void testSaveSeller() {
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        SellerEntity expectedSeller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, new RoleEntity(1L, "Cliente"), "Company", true, "banco", "cuenta", 1);
        when(sellerService.saveSeller(Mockito.any(SellerEntity.class))).thenReturn(expectedSeller);
        SellerEntity actualSeller = sellerController.saveSeller(new SellerEntity());
        assertEquals(expectedSeller, actualSeller);
    }

    @Test
    public void testLoginSuccessful() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role, "Company", true,  "banco", "cuenta", 1);
        when(sellerService.validateSeller(Mockito.anyString(), Mockito.anyString())).thenReturn(seller);
        when(request.getSession()).thenReturn(session);
        ResponseEntity<?> response = sellerController.login(seller, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(session).setAttribute("seller", seller);
    }

    @Test
    public void testLoginFailed() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role, "Company", true,  "banco", "cuenta", 1);
        when(sellerService.validateSeller(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        ResponseEntity<?> response = sellerController.login(seller, request);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(session, Mockito.never()).setAttribute(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void sellerInformationUpdateCompanyNameTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role, "companyName", true,  "banco", "cuenta", 1);
        ResponseEntity<?> responseEntity = sellerController.sellerInformationUpdateCompanyName(seller);
        verify(sellerService, times(1)).updateCoverageIdAndCompanyNameSellerByEmail(seller.getEmail(), seller.getCompanyName(), seller.getCoverageID(), seller.getBank(), seller.getBankAccountType(), seller.getBankAccountNumber());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void getSellerByIdTest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role, "companyName", true,  "banco", "cuenta", 1);
        when(sellerService.getSellerById(anyLong())).thenReturn(Optional.of(seller));
        ResponseEntity<SellerEntity> response = sellerController.getSellerById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(seller, response.getBody());
    }

    @Test
    public void testGetSellerById_withNonExistingSeller_shouldReturnNotFound() {
        long nonExistingSellerId = 1234L;
        when(sellerService.getSellerById(nonExistingSellerId)).thenReturn(Optional.empty());

        ResponseEntity<SellerEntity> response = sellerController.getSellerById(nonExistingSellerId);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    public void testDeleteSellers() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("SE ELIMINARON LOS VENDEDORES CORRECTAMENTE");
        doNothing().when(sellerService).deleteSellers();
        ResponseEntity<String> response = sellerController.deleteSellers();
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testDeleteSellerById() {
        Long id = Long.valueOf("9999");
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("VENDEDOR CON ID " + id + " ELIMINADO CORRECTAMENTE\n");
        when(sellerService.existsSellerById(id)).thenReturn(true);
        doNothing().when(sellerService).deleteSellerById(id);
        ResponseEntity<String> response = sellerController.deleteSellerById(id);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testDeleteSellerByIdNotFound() {
        Long id = Long.valueOf("9999");
        ResponseEntity<String> expectedResponse = ResponseEntity.notFound().build();
        when(sellerService.existsSellerById(id)).thenReturn(false);
        ResponseEntity<String> response = sellerController.deleteSellerById(id);
        assertEquals(expectedResponse, response);
    }
}