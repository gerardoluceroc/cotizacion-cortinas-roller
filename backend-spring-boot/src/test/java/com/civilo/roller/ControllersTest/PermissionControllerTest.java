package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.PermissionEntity;
import com.civilo.roller.controllers.PermissionController;
import com.civilo.roller.services.PermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class PermissionControllerTest {
    @Mock
    private PermissionService permissionService;
    @InjectMocks
    private PermissionController permissionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPermissions() {
        List<PermissionEntity> expectedPermissions = new ArrayList<>();
        expectedPermissions.add(new PermissionEntity(1L, "Permission 1", null));
        expectedPermissions.add(new PermissionEntity(2L, "Permission 2", null));
        when(permissionService.getPermissions()).thenReturn(expectedPermissions);
        List<PermissionEntity> actualPermissions = permissionController.getPermissions();
        assertEquals(expectedPermissions, actualPermissions);
    }
    /*
    @Test
    void testSavePermission() {
        PermissionEntity expectedPermission = new PermissionEntity(1L, "Permission 1", null);
        Mockito.when(permissionService.savePermission(Mockito.any(PermissionEntity.class))).thenReturn(expectedPermission);
        PermissionEntity actualPermission = permissionController.createPermission(new PermissionEntity());
        assertEquals(expectedPermission, actualPermission);
    }
    */
    @Test
    public void testGetPermissionById() {
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", null);
        when(permissionService.getPermissionById(permission.getPermissionID())).thenReturn(Optional.of(permission));
        ResponseEntity<PermissionEntity> response = permissionController.getPermissionById(permission.getPermissionID());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(permission, response.getBody());
    }

    @Test
    public void testGetPermissionByIdNotFound() {
        long id = 9999L;
        when(permissionService.getPermissionById(id)).thenReturn(Optional.empty());
        ResponseEntity<PermissionEntity> response = permissionController.getPermissionById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreatePermission() {
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", null);
        when(permissionService.validatePermission(permission.getPermission())).thenReturn(Optional.empty());
        ResponseEntity<?> response = permissionController.createPermission(permission);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreatePermissionConflict() {
        PermissionEntity permission = new PermissionEntity(Long.valueOf("9999"), "Permission 1", null);
        when(permissionService.validatePermission(permission.getPermission())).thenReturn(Optional.of(permission));
        ResponseEntity<?> response = permissionController.createPermission(permission);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El permiso ingresado ya existe", response.getBody());
    }

    @Test
    public void testUpdatePermission() {
        long id = 9999L;
        PermissionEntity permission = new PermissionEntity(id, "Permission 1 updated", null);
        when(permissionService.getPermissionById(id)).thenReturn(Optional.of(new PermissionEntity(id, "Permission 1", null)));
        when(permissionService.validatePermission(permission.getPermission())).thenReturn(Optional.empty());
        ResponseEntity<?> response = permissionController.updateUser(id, permission);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdatePermissionNotFound() {
        long id = 9999L;
        PermissionEntity permission = new PermissionEntity(id, "Permission 1 updated", null);
        when(permissionService.getPermissionById(id)).thenReturn(Optional.empty());
        ResponseEntity<?> response = permissionController.updateUser(id, permission);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("El permiso con el ID especificado no existe.", response.getBody());
    }

    @Test
    public void testUpdatePermissionConflict() {
        long id = 9999L;
        PermissionEntity permission = new PermissionEntity(id, "Permission 1 updated", null);
        when(permissionService.getPermissionById(id)).thenReturn(Optional.of(new PermissionEntity(id, "Permission 1", null)));
        when(permissionService.validatePermission(permission.getPermission())).thenReturn(Optional.of(permission));
        ResponseEntity<?> response = permissionController.updateUser(id, permission);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El permiso ingresado ya existe", response.getBody());
    }

    @Test
    public void testDeletePermissions() {
        ResponseEntity<String> response = permissionController.deletePermissions();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SE ELIMINARON LOS PERMISOS CORRECTAMENTE", response.getBody());
    }

    @Test
    public void testDeletePermissionById() {
        Long id = Long.valueOf("9999");
        Mockito.when(permissionService.existsPermissionById(id)).thenReturn(true);
        ResponseEntity<String> responseEntity = permissionController.deletePermissionById(id);
        Mockito.verify(permissionService, Mockito.times(1)).deletePermissionById(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeletePermissionById_NotFound() {
        Long id = Long.valueOf("9999");
        Mockito.when(permissionService.existsPermissionById(id)).thenReturn(false);
        ResponseEntity<String> responseEntity = permissionController.deletePermissionById(id);
        Mockito.verify(permissionService, Mockito.times(0)).deletePermissionById(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
