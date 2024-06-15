package com.civilo.roller.repositories;

import com.civilo.roller.Entities.QuoteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends CrudRepository<QuoteEntity, Long> {
}
