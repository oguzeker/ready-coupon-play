package com.bilyoner.assignment.couponapi.repository;

import com.bilyoner.assignment.couponapi.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findByIdIn(List<Long> eventIds);

}
