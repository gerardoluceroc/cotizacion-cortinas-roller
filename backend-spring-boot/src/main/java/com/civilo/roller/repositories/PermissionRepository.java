package com.civilo.roller.repositories;

import com.civilo.roller.Entities.PermissionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends CrudRepository<PermissionEntity, Long> {

    PermissionEntity findByPermission(String permission);

}
