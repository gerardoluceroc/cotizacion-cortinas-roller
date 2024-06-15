package com.civilo.roller.ControllersTest;

import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.controllers.RoleController;
import com.civilo.roller.services.RoleService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {
    @Mock
    private RoleService roleService;
    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRoles() {
        List<RoleEntity> expectedRoles = new ArrayList<>();
        expectedRoles.add(new RoleEntity(1L, "Type 1"));
        expectedRoles.add(new RoleEntity(2L, "Type 2"));
        when(roleService.getRoles()).thenReturn(expectedRoles);
        List<RoleEntity> actualRoles = roleController.getRoles();
        assertEquals(expectedRoles, actualRoles);
    }

    @Test
    public void getRoleByIdTest() {
        long roleId = 1L;
        RoleEntity role = new RoleEntity(roleId, "Cliente");
        when(roleService.getRoleById(roleId)).thenReturn(Optional.of(role));
        ResponseEntity<RoleEntity> response = roleController.getRoleById(roleId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(role, response.getBody());
    }

    @Test
    public void getRoleByIdNotFoundTest() {
        long roleId = 1L;
        when(roleService.getRoleById(roleId)).thenReturn(Optional.empty());
        ResponseEntity<RoleEntity> response = roleController.getRoleById(roleId);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void createRoleTest() {
        RoleEntity role = new RoleEntity(1L, "Nuevo rol");
        when(roleService.validateAccountType(role.getAccountType())).thenReturn(Optional.empty());
        ResponseEntity<?> response = roleController.createRole(role);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(roleService, times(1)).createRole(role);
    }

    @Test
    public void createRoleAlreadyExistsTest() {
        RoleEntity role = new RoleEntity(1L, "Nuevo rol");
        when(roleService.validateAccountType(role.getAccountType())).thenReturn(Optional.of(role));
        ResponseEntity<?> response = roleController.createRole(role);
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Ya existe el rol para el tipo de cuenta ingresado", response.getBody());
    }

    @Test
    public void updateRoleTest() {
        long roleId = 1L;
        RoleEntity existingRole = new RoleEntity(roleId, "Rol existente");
        RoleEntity updatedRole = new RoleEntity(roleId, "Rol actualizado");
        when(roleService.getRoleById(roleId)).thenReturn(Optional.of(existingRole));
        when(roleService.validateAccountType(updatedRole.getAccountType())).thenReturn(Optional.empty());
        ResponseEntity<?> response = roleController.updateRole(roleId, updatedRole);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(roleService, times(1)).updateRole(roleId, updatedRole);
    }

    @Test
    public void updateRoleNotFoundTest() {
        long roleId = 1L;
        RoleEntity updatedRole = new RoleEntity(roleId, "Rol actualizado");
        when(roleService.getRoleById(roleId)).thenReturn(Optional.empty());
        ResponseEntity<?> response = roleController.updateRole(roleId, updatedRole);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("El rol con el ID especificado no existe.", response.getBody());
    }

    @Test
    public void updateRole_shouldReturnNotFound_whenRoleDoesNotExist() {
        long id = 1L;
        RoleEntity roleToUpdate = new RoleEntity(id, "Nuevo Rol");
        Optional<RoleEntity> existingRole = Optional.empty();
        when(roleService.getRoleById(id)).thenReturn(existingRole);
        ResponseEntity<?> response = roleController.updateRole(id, roleToUpdate);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(roleService, never()).updateRole(eq(id), any());
    }

    @Test
    void testUpdateRole_Conflict() {
        long id = 1L;
        RoleEntity roleEntity = new RoleEntity(id, "Admin");
        when(roleService.getRoleById(id)).thenReturn(Optional.of(roleEntity));
        when(roleService.validateAccountType(roleEntity.getAccountType())).thenReturn(Optional.of(roleEntity));
        ResponseEntity<?> response = roleController.updateRole(id, roleEntity);
        verify(roleService).getRoleById(id);
        verify(roleService).validateAccountType(roleEntity.getAccountType());
        verify(roleService, never()).updateRole(anyLong(), any(RoleEntity.class));
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void testDeleteRoles() {
        ResponseEntity<String> response = roleController.deleteRoles();
        verify(roleService).deleteRoles();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteRoleById() {
        long id = 1L;
        when(roleService.existsRoleById(id)).thenReturn(true);
        ResponseEntity<String> response = roleController.deleteRoleById(id);
        verify(roleService).deleteRoleById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteRoleById_NotFound() {
        long id = 1L;
        when(roleService.existsRoleById(id)).thenReturn(false);
        ResponseEntity<String> response = roleController.deleteRoleById(id);
        verify(roleService, never()).deleteRoleById(anyLong());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}