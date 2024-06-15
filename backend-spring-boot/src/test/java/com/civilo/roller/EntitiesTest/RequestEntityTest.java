package com.civilo.roller.EntitiesTest;

import com.civilo.roller.Entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class RequestEntityTest {
    @Mock
    UserEntity user;

    @Mock
    CoverageEntity coverage;

    @Mock
    CurtainEntity curtains;

    @Mock
    StatusEntity status;

    @Test
    void testRequestEntity() {
        Long requestID = 1L;
        String description = "Test request";
        LocalDate deadline = LocalDate.now().plusDays(7);
        LocalDate admissionDate = LocalDate.now();
        LocalDate closingDate = LocalDate.now().plusDays(2);
        String reason = "Test reason";
        List<Integer> userID = new ArrayList<>();
        userID.add(1);
        userID.add(2);
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestID(requestID);
        requestEntity.setDescription(description);
        requestEntity.setDeadline(deadline);
        requestEntity.setAdmissionDate(admissionDate);
        requestEntity.setClosingDate(closingDate);
        requestEntity.setReason(reason);
        requestEntity.setUserID(userID);
        requestEntity.setUser(user);
        requestEntity.setCoverage(coverage);
        requestEntity.setCurtain(curtains);
        requestEntity.setStatus(status);
        assertNotNull(requestEntity);
        assertEquals(requestID, requestEntity.getRequestID());
        assertEquals(description, requestEntity.getDescription());
        assertEquals(deadline, requestEntity.getDeadline());
        assertEquals(admissionDate, requestEntity.getAdmissionDate());
        assertEquals(closingDate, requestEntity.getClosingDate());
        assertEquals(reason, requestEntity.getReason());
        assertEquals(userID, requestEntity.getUserID());
        assertEquals(user, requestEntity.getUser());
        assertEquals(coverage, requestEntity.getCoverage());
        assertEquals(curtains, requestEntity.getCurtain());
        assertEquals(status, requestEntity.getStatus());
    }
}
