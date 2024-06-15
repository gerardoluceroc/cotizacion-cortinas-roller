package com.civilo.roller.repositories;

import com.civilo.roller.Entities.QuoteSummaryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteSummaryRepository extends CrudRepository<QuoteSummaryEntity, Long> {
}
