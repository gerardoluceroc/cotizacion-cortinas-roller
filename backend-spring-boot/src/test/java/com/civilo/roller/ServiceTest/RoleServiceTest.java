package com.civilo.roller.ServiceTest;

import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.repositories.RoleRepository;
import com.civilo.roller.services.RoleService;
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
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    RoleService roleService;

    @Test
    void saveRole(){
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Type 1");
        when(roleRepository.save(role)).thenReturn(role);
        final RoleEntity currentResponse = roleService.createRole(role);
        assertEquals(role,currentResponse);
    }

    @Test
    void getRoles(){
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Type 1");
        List<RoleEntity> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(role);
        when((List<RoleEntity>) roleRepository.findAll()).thenReturn(expectedAnswer);
        final List<RoleEntity> currentResponse = roleService.getRoles();
        assertEquals(expectedAnswer, currentResponse);
    }

    @Test
    void getRoleIdByAccountType() {
        String accountType = "some account type";
        Long roleId = 123L;
        when(roleRepository.findIdByAccountType(accountType)).thenReturn(roleId);
        assertEquals(roleId, roleService.getRoleIdByAccountType(accountType));
    }

    @Test
    public void testGetRoleById() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        when(roleRepository.findById(role.getRoleID())).thenReturn(Optional.of(role));
        Optional<RoleEntity> result = roleService.getRoleById(role.getRoleID());
        assertTrue(result.isPresent());
        assertEquals(role.getAccountType(), result.get().getAccountType());
    }

    @Test
    public void testValidateAccountType() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        when(roleRepository.findByAccountType(role.getAccountType())).thenReturn(role);
        Optional<RoleEntity> result = roleService.validateAccountType(role.getAccountType());
        assertTrue(result.isPresent());
        assertEquals(role.getRoleID(), result.get().getRoleID());
    }

    @Test
    public void testUpdateRole() {
        RoleEntity existingRole = new RoleEntity(Long.valueOf("9999"), "Cliente");
        RoleEntity updatedRole = new RoleEntity(Long.valueOf("9999"), "Vendedor");
        when(roleRepository.findById(existingRole.getRoleID())).thenReturn(Optional.of(existingRole));
        when(roleRepository.save(existingRole)).thenReturn(updatedRole);
        RoleEntity result = roleService.updateRole(existingRole.getRoleID(), updatedRole);
        assertEquals(updatedRole.getAccountType(), result.getAccountType());
    }

    @Test
    public void testDeleteRoles() {
        roleService.deleteRoles();
        verify(roleRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteRoleById() {
        Long roleId = Long.valueOf("9999");
        roleService.deleteRoleById(roleId);
        verify(roleRepository, times(1)).deleteById(roleId);
    }

    @Test
    public void testExistsRoleById() {
        RoleEntity role = new RoleEntity(Long.valueOf("9999"), "Cliente");
        when(roleRepository.findById(role.getRoleID())).thenReturn(Optional.of(role));
        boolean result = roleService.existsRoleById(role.getRoleID());
        assertTrue(result);
    }
}
