package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.PermissionEntity;
import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.repositories.PermissionRepository;
import com.civilo.roller.services.PermissionService;
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
public class PermissionServiceTest {
    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    PermissionService permissionService;

    @Test
    void savePermission(){
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permiso 1", null);
        when(permissionRepository.save(permission)).thenReturn(permission);
        final PermissionEntity currentResponse = permissionService.savePermission(permission);
        assertEquals(permission,currentResponse);
    }

    @Test
    void getPermissions(){
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permiso 1", null);
        List<PermissionEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(permission);
        when((List<PermissionEntity>) permissionRepository.findAll()).thenReturn(expectedAnswer);
        final List<PermissionEntity> currentResponse = permissionService.getPermissions();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    void rolePermissions(){
        RoleEntity role = new RoleEntity(Long.valueOf("1111"), "Tipo de cuenta 1");
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permiso 1", role);
        List<PermissionEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(permission);
        when((List<PermissionEntity>) permissionRepository.findAll()).thenReturn(expectedAnswer);
        String currentAnswer = permissionService.rolePermissions(Long.valueOf("1234"));
        assertEquals("", currentAnswer);
    }

    @Test
    void rolePermissions2(){
        RoleEntity role = new RoleEntity(Long.valueOf("1111"), "Tipo de cuenta 1");
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permiso 1", role);
        List<PermissionEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(permission);
        when((List<PermissionEntity>) permissionRepository.findAll()).thenReturn(expectedAnswer);
        String currentAnswer = "";
        currentAnswer = currentAnswer + permissionService.rolePermissions(role.getRoleID());
        assertEquals("Permiso 1\n                  ", currentAnswer);
    }

    @Test
    void testGetPermissionById() {
        Long id = Long.valueOf("9999");
        PermissionEntity expectedPermission = new PermissionEntity(id, "Permission 1", null);
        when(permissionRepository.findById(id)).thenReturn(Optional.of(expectedPermission));
        Optional<PermissionEntity> actualPermission = permissionService.getPermissionById(id);
        assertEquals(expectedPermission, actualPermission.orElse(null));
    }

    @Test
    public void createPermissionTest() {
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", null);
        when(permissionRepository.save(permission)).thenReturn(permission);
        assertEquals(permission, permissionService.createPermission(permission));
        verify(permissionRepository, times(1)).save(permission);
    }

    @Test
    public void validatePermissionTest() {
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", null);
        when(permissionRepository.findByPermission("Permission 1")).thenReturn(permission);
        Optional<PermissionEntity> result = permissionService.validatePermission("Permission 1");
        assertTrue(result.isPresent());
        assertEquals(permission, result.get());
        verify(permissionRepository, times(1)).findByPermission("Permission 1");
    }

    @Test
    public void updatePermissionTest() {
        PermissionEntity existingPermission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", null);
        when(permissionRepository.findById(existingPermission.getPermissionID())).thenReturn(Optional.of(existingPermission));
        PermissionEntity updatedPermission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", null);
        when(permissionRepository.save(existingPermission)).thenReturn(updatedPermission);
        assertEquals(updatedPermission, permissionService.updatePermission(existingPermission.getPermissionID(), updatedPermission));
        verify(permissionRepository, times(1)).findById(existingPermission.getPermissionID());
        verify(permissionRepository, times(1)).save(existingPermission);
    }

    @Test
    public void deletePermissionsTest() {
        permissionService.deletePermissions();
        verify(permissionRepository, times(1)).deleteAll();
    }

    @Test
    public void deletePermissionByIdTest() {
        Long permissionId = Long.valueOf("9999");
        permissionService.deletePermissionById(permissionId);
        verify(permissionRepository, times(1)).deleteById(permissionId);
    }

    @Test
    public void existsPermissionByIdTest() {
        Long permissionId = Long.valueOf("9999");
        when(permissionRepository.findById(permissionId)).thenReturn(Optional.of(new PermissionEntity(permissionId, "Permission 1", null)));
        assertTrue(permissionService.existsPermissionById(permissionId));
        verify(permissionRepository, times(1)).findById(permissionId);
    }
}