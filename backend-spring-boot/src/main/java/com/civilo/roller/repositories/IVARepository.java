package com.civilo.roller.repositories;

import com.civilo.roller.Entities.IVAEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVARepository extends CrudRepository<IVAEntity, Long> {
}
