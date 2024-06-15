package com.civilo.roller.repositories;

import com.civilo.roller.Entities.RoleEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    //Se consulta la id de un rol especifico
    @Query(value = "select roleid idrol from roles r where r.account_type = :accountType", nativeQuery = true)
    Long findIdByAccountType(@Param("accountType") String accountType);

    RoleEntity findByAccountType(String accountType);

}
