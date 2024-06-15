package com.civilo.roller.repositories;

import com.civilo.roller.Entities.CurtainEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CurtainRepository extends CrudRepository<CurtainEntity, Long> {

    //Se consulta la id de una cortina especifica
    @Query(value = "select curtainid idcurtain from curtain r where r.curtain_type = :curtainType", nativeQuery = true)
    Long findIdByCurtainType(@Param("curtainType") String curtainType);

    CurtainEntity findByCurtainType(String curtainName);
}
