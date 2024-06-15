package com.civilo.roller.repositories;

import com.civilo.roller.Entities.PipeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PipeRepository extends CrudRepository<PipeEntity, Long> {

    PipeEntity findByPipeName(String pipeName);
}
