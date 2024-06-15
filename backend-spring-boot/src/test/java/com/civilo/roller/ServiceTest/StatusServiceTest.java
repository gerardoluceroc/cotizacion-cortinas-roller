package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.StatusEntity;
import com.civilo.roller.repositories.StatusRepository;
import com.civilo.roller.services.StatusService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class StatusServiceTest {
    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    StatusService statusService;

    @Test
    void saveStatus(){
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status");
        Mockito.when(statusRepository.save(status)).thenReturn(status);
        final StatusEntity currentResponse = statusService.createStatus(status);
        assertEquals(status,currentResponse);
    }

    @Test
    void getStatusById() {
        Long statusId = 123L;
        StatusEntity status1 = new StatusEntity(1L, "Status 1");
        StatusEntity status2 = new StatusEntity(2L, "Status 2");
        List<StatusEntity> statusEntities = new ArrayList<>();
        statusEntities.add(status1);
        statusEntities.add(status2);
        Optional<StatusEntity> myStatus = statusService.getStatusById(statusId);
        assertEquals(2, statusEntities.size());
        assertEquals(status1, statusEntities.get(0));
    }

    @Test
    public void testValidateStatusName() {
        StatusEntity status1 = new StatusEntity(Long.valueOf("9999"), "Status 1");
        when(statusRepository.findByStatusName(status1.getStatusName())).thenReturn(status1);
        Optional<StatusEntity> result = statusService.validateStatusName(status1.getStatusName());
        assertEquals(status1, result.get());
    }

    @Test
    void getStatus(){
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status");
        List<StatusEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(status);
        Mockito.when((List<StatusEntity>) statusRepository.findAll()).thenReturn(expectedAnswer);
        final List<StatusEntity> currentResponse = statusService.getStatus();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    void testUpdateStatus() {
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        when(statusRepository.findById(status.getStatusID())).thenReturn(Optional.of(status));
        when(statusRepository.save(status)).thenReturn(status);
        StatusEntity updatedStatus = statusService.updateStatus(status.getStatusID(), status);
        verify(statusRepository, times(1)).findById(status.getStatusID());
        verify(statusRepository, times(1)).save(status);
        assertEquals(status, updatedStatus);
    }

    @Test
    public void testDeleteStatus() {
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        statusService.deleteStatus();
        verify(statusRepository, times(1)).deleteAll();
    }

    @Test
    public void deleteStatusById_ShouldCallDeleteById() {
        Long id = Long.valueOf("9999");
        statusService.deleteStatusById(id);
        verify(statusRepository).deleteById(id);
    }

    @Test
    public void testExistsStatusById() {
        StatusEntity status = new StatusEntity(Long.valueOf("9999"), "Status 1");
        when(statusRepository.findById(status.getStatusID())).thenReturn(Optional.of(status));
        boolean exists = statusService.existsStatusById(status.getStatusID());
        assertTrue(exists);
    }

    @Test
    public void testGetStatusIdByStatusName() {
        String statusName = "Status 1";
        Long expectedId = Long.valueOf("9999");
        when(statusRepository.findIdByStatusName(statusName)).thenReturn(expectedId);
        Long actualId = statusService.getStatusIdByStatusName(statusName);
        assertEquals(expectedId, actualId);
    }
}