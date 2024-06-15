package com.civilo.roller.repositories;

import com.civilo.roller.Entities.StatusEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends CrudRepository<StatusEntity, Long> {

    //Se consulta la id de un estado especifico
    @Query(value = "select statusid idstatus from status r where r.status_name = :statusName", nativeQuery = true)
    Long findIdByStatusName(@Param("statusName") String statusName);
    
    StatusEntity findByStatusName(String statusName);

}