package com.civilo.roller.repositories;

import com.civilo.roller.Entities.ProfitMarginEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitMarginRepository extends CrudRepository<ProfitMarginEntity, Long> {

    ProfitMarginEntity findByDecimalProfitMargin(float decimalProfitMargin);
}
