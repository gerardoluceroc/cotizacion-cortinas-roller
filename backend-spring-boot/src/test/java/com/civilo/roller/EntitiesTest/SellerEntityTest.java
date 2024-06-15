package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class SellerEntityTest {
    @Test
    void testConstructor() {
        Long userID = 1L;
        String name = "John";
        String surname = "Doe";
        String email = "johndoe@example.com";
        String password = "password123";
        String phoneNumber = "1234567890";
        String commune = "Santiago";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        int age = 31;
        RoleEntity role = new RoleEntity(1L, "Cliente");
        String companyName = "Example Inc.";
        boolean disponibility = true;
        List<Integer> coverageID = Arrays.asList(1, 2, 3);
        List<QuoteEntity> quoteEntities = new ArrayList<>();
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        String rut = "rut";
        SellerEntity seller = new SellerEntity(userID, name, surname, email, password, rut, phoneNumber, commune, birthDate, age, startTime, endTime, role, companyName, disponibility, "banco", "cuenta", 1);
        seller.setUserID(1L);
        seller.setName("John");
        seller.setSurname("Doe");
        seller.setEmail("johndoe@example.com");
        seller.setPassword("password123");
        seller.setPhoneNumber("1234567890");
        seller.setCommune("Santiago");
        seller.setBirthDate(LocalDate.of(1990, 1, 1));
        seller.setAge(31);
        seller.setCoverageID(coverageID);
        seller.setQuoteEntities(quoteEntities);
        assertEquals(userID, seller.getUserID());
        assertEquals(name, seller.getName());
        assertEquals(surname, seller.getSurname());
        assertEquals(email, seller.getEmail());
        assertEquals(password, seller.getPassword());
        assertEquals(phoneNumber, seller.getPhoneNumber());
        assertEquals(commune, seller.getCommune());
        assertEquals(birthDate, seller.getBirthDate());
        assertEquals(age, seller.getAge());
        assertEquals(companyName, seller.getCompanyName());
        assertEquals(disponibility, seller.isDisponibility());
        assertEquals(coverageID, seller.getCoverageID());
        assertEquals(quoteEntities, seller.getQuoteEntities());
        assertEquals(startTime, seller.getStartTime());
        assertEquals(endTime, seller.getEndTime());
        assertEquals("rut", seller.getRut());
        assertEquals("banco", seller.getBank());
        assertEquals("cuenta", seller.getBankAccountType());
        assertEquals(1, seller.getBankAccountNumber());
    }

    @Test
    void testNoArgsConstructor() {
        SellerEntity seller = new SellerEntity();
        assertNotNull(seller);
        assertNull(seller.getUserID());
        assertNull(seller.getName());
        assertNull(seller.getSurname());
        assertNull(seller.getEmail());
        assertNull(seller.getPassword());
        assertNull(seller.getPhoneNumber());
        assertNull(seller.getCommune());
        assertNull(seller.getBirthDate());
        assertEquals(0, seller.getAge());
        assertNull(seller.getCompanyName());
        assertFalse(seller.isDisponibility());
        assertNull(seller.getCoverageID());
        assertNull(seller.getQuoteEntities());
    }

    @Test
    void testAllArgsConstructorWithNullValues() {
        Long userID = null;
        String name = null;
        String surname = null;
        String email = null;
        String password = null;
        String rut = null;
        String phoneNumber = null;
        String commune = null;
        LocalDate birthDate = null;
        int age = 0;
        LocalTime startTime = null;
        LocalTime endTime = null;
        RoleEntity role = null;
        String companyName = null;
        boolean disponibility = false;
        String bank = null;
        String bankAccountType = null;
        int bankAccountNumber = 0;

        SellerEntity seller = new SellerEntity(userID, name, surname, email, password, rut, phoneNumber, commune,
                birthDate, age, startTime, endTime, role, companyName, disponibility, bank, bankAccountType,
                bankAccountNumber);

        assertNull(seller.getUserID());
        assertNull(seller.getName());
        assertNull(seller.getSurname());
        assertNull(seller.getEmail());
        assertNull(seller.getPassword());
        assertNull(seller.getRut());
        assertNull(seller.getPhoneNumber());
        assertNull(seller.getCommune());
        assertNull(seller.getBirthDate());
        assertEquals(0, seller.getAge());
        assertNull(seller.getStartTime());
        assertNull(seller.getEndTime());
        assertNull(seller.getRole());
        assertNull(seller.getCompanyName());
        assertFalse(seller.isDisponibility());
        assertNull(seller.getBank());
        assertNull(seller.getBankAccountType());
        assertEquals(0, seller.getBankAccountNumber());
        assertNull(seller.getCoverageID());
        assertNull(seller.getQuoteEntities());
    }


}