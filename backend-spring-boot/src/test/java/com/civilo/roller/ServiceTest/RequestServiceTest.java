package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.*;
import com.civilo.roller.repositories.RequestRepository;
import com.civilo.roller.services.*;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {
    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    RequestService requestService;

    @Mock
    SellerService sellerService;

    @Mock
    StatusService statusService;

    @Mock
    RoleService roleService;

    @Mock
    PermissionService permissionService;

    @Test
    void saveRequest(){
        RequestEntity request = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason",1, null, null, null, null, null);
        when(requestRepository.save(request)).thenReturn(request);
        final RequestEntity currentResponse = requestService.saveRequest(request);
        assertEquals(request,currentResponse);
    }

    @Test
    void getRequests(){
        RequestEntity request = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, null, null, null, null);
        List<RequestEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(request);
        when((List<RequestEntity>) requestRepository.findAll()).thenReturn(expectedAnswer);
        final List<RequestEntity> currentResponse = requestService.getRequests();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    void getRequestBySellerId() {
        Long sellerId = 123L;
        RequestEntity request1 = new RequestEntity(1L, "Description 1", LocalDate.now(), LocalDate.now(), LocalDate.now(), "Reason 1", 123, null, null, null, null, null);
        RequestEntity request2 = new RequestEntity(2L, "Description 2", LocalDate.now(), LocalDate.now(), LocalDate.now(), "Reason 2", 456, null, null, null, null, null);
        List<RequestEntity> requests = new ArrayList<>();
        requests.add(request1);
        requests.add(request2);
        when(requestService.getRequests()).thenReturn(requests);
        List<RequestEntity> myRequest = requestService.getRequestBySellerId(sellerId);
        assertEquals(1, myRequest.size());
        assertEquals(request1, myRequest.get(0));
    }

    @Test
    void getRequestByIdShouldReturnRequestEntity() {
        Long id = Long.valueOf("9999");
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(id, "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        when(requestRepository.findById(id)).thenReturn(Optional.of(requestEntity));
        Optional<RequestEntity> result = requestService.getRequestById(id);
        assertTrue(result.isPresent());
        assertEquals(requestEntity, result.get());
        verify(requestRepository, times(1)).findById(id);
    }

    @Test
    public void testCreateRequest() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        when(requestRepository.save(requestEntity)).thenReturn(requestEntity);
        RequestEntity result = requestService.createRequest(requestEntity);
        verify(requestRepository, times(1)).save(requestEntity);
        assertEquals(requestEntity, result);
    }

    @Test
    void updateRequestTest() {
        Long requestID = Long.valueOf("9999");
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(requestID, "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        when(requestRepository.findById(requestID)).thenReturn(Optional.of(requestEntity));
        RequestEntity updatedRequestEntity = new RequestEntity(requestID, "Updated description", LocalDate.of(2022,9,21), LocalDate.of(2022,9,21), LocalDate.of(2022,9,21), "Updated reason", 2, null, user, null, null, null);
        when(requestRepository.save(any(RequestEntity.class))).thenReturn(updatedRequestEntity);
        RequestEntity result = requestService.updateRequest(requestID, updatedRequestEntity);
        assertEquals(updatedRequestEntity, result);
    }

    @Test
    public void testDeleteRequest() {
        doNothing().when(requestRepository).deleteAll();
        requestService.deleteRequest();
        verify(requestRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteRequestById() {
        Long id = Long.valueOf("9999");
        doNothing().when(requestRepository).deleteById(id);
        requestService.deleteRequestById(id);
        verify(requestRepository, times(1)).deleteById(id);
    }

    @Test
    public void testExistsRequestById() {
        Long id = Long.valueOf("9999");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        when(requestRepository.findById(id)).thenReturn(Optional.of(new RequestEntity(id, "Description", LocalDate.of(2022, 9, 20), LocalDate.of(2022, 9, 20), LocalDate.of(2022, 9, 20), "Reason", 1, null, new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022, 9, 20), 20, startTime, endTime, new RoleEntity(Long.valueOf("9999"), "Cliente")), null, null, null)));
        assertTrue(requestService.existsRequestById(id));
        assertFalse(requestService.existsRequestById(Long.valueOf("8888")));
        verify(requestRepository, times(2)).findById(anyLong());
    }

    @Test
    public void testAutomaticAssignment() {
        List<RequestEntity> requestEntities = new ArrayList<>();
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        requestEntities.add(requestEntity);
        List<SellerEntity> sellerEntities = new ArrayList<>();
        RoleEntity sellerRole = new RoleEntity(Long.valueOf("9999"), "Vendedor");
        SellerEntity seller = new SellerEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, sellerRole, "companyName", true, "banco", "cuenta", 1);
        sellerEntities.add(seller);
        List<StatusEntity> statusEntities = new ArrayList<>();
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        statusEntities.add(status);
        statusEntities.add(status); // Para que la lista contenga al menos dos elementos
        when(requestRepository.findAll()).thenReturn(requestEntities);
        when(sellerService.getSellers()).thenReturn(sellerEntities);
        requestService.automaticAssignment();
        verify(requestRepository, atLeastOnce()).findAll();
        verify(sellerService, atLeastOnce()).getSellers();
    }

    @Test
    public void testAutomaticAssignment2() {
        List<RequestEntity> requestEntities = new ArrayList<>();
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("0"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022, 9, 20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("1"), "Description", LocalDate.of(2022, 9, 20), LocalDate.of(2022, 9, 20), LocalDate.of(2022, 9, 20), "Reason", 0, null, user, null, null, null);
        requestEntities.add(requestEntity);

        List<SellerEntity> sellerEntities = new ArrayList<>();
        RoleEntity sellerRole = new RoleEntity(Long.valueOf("9999"), "Vendedor");
        SellerEntity seller = new SellerEntity(Long.valueOf("1"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022, 9, 20), 20, startTime, endTime, sellerRole, "companyName", true, "banco", "cuenta", 1);
        sellerEntities.add(seller);

        List<StatusEntity> statusEntities = new ArrayList<>();
        StatusEntity status1 = new StatusEntity(Long.valueOf("9999"), "Status 1");
        StatusEntity status2 = new StatusEntity(Long.valueOf("9998"), "Status 2");
        statusEntities.add(status1);
        statusEntities.add(status2);

        // Mocking the repository and service behavior
        when(requestService.getRequests()).thenReturn(requestEntities);
        when(sellerService.getSellers()).thenReturn(sellerEntities);
        when(statusService.getStatus()).thenReturn(statusEntities);

        // Calling the service method
        requestService.automaticAssignment();

        // Verifying the interactions
        verify(sellerService, atLeastOnce()).getSellers();
        verify(requestRepository, atLeastOnce()).save(any(RequestEntity.class));
    }



    @Test
    void testGetRequestByUserId() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        LocalTime startTime = LocalTime.of(15, 30, 0);
        LocalTime endTime = LocalTime.of(16, 30, 0);
        UserEntity user = new UserEntity(Long.valueOf("9999"), "Name", "Surname", "Email", "Password", "rut", "0 1234 5678", "Commune", LocalDate.of(2022,9,20), 20, startTime, endTime, role);
        RequestEntity requestEntity = new RequestEntity(Long.valueOf("9999"), "Description", LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), LocalDate.of(2022,9,20), "Reason", 1, null, user, null, null, null);
        ArrayList<RequestEntity> expected = new ArrayList<>();
        expected.add(requestEntity);
        when(requestRepository.findRequestByUserId(any(Long.class))).thenReturn(expected);
        ArrayList<RequestEntity> result = requestService.getRequestByUserId(Long.valueOf("9999"));
        assertEquals(expected, result);
    }
}