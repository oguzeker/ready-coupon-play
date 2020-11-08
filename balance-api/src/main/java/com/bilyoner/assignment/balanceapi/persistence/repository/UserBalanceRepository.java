package com.bilyoner.assignment.balanceapi.persistence.repository;

import com.bilyoner.assignment.balanceapi.persistence.entity.UserBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserBalanceRepository extends JpaRepository<UserBalanceEntity, Long> {

    @Query("FROM UserBalanceEntity b JOIN FETCH b.historyEntities h WHERE b.userId = :userId")
    Optional<UserBalanceEntity> getUserBalanceEntitiesWithHistory(@Param("userId") Long userId);

}
