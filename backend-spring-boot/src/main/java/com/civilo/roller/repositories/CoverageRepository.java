package com.civilo.roller.repositories;

import com.civilo.roller.Entities.CoverageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverageRepository extends CrudRepository<CoverageEntity, Long> {

    //Se consulta la id de una comuna especifica
    //@Query(value = "select coverageid idcoverage from coverages r where r.commune = :commune", nativeQuery = true)
    //Long findIdByCommune(@Param("commune") String commune);
    
    CoverageEntity findByCommune(String commune);

}