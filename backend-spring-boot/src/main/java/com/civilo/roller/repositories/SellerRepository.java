package com.civilo.roller.repositories;

import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.Entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends CrudRepository<SellerEntity, Long> {
    SellerEntity findByEmail(String email);
}
