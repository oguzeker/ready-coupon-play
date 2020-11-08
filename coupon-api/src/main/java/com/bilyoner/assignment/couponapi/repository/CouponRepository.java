package com.bilyoner.assignment.couponapi.repository;

import com.bilyoner.assignment.couponapi.entity.CouponEntity;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    Page<CouponEntity> findByStatus(CouponStatusEnum status, Pageable paging);

    Optional<CouponEntity> findByIdAndStatus(Long couponId, CouponStatusEnum status);

    List<CouponEntity> findByIdIn(List<Long> couponIds);

    List<CouponEntity> findByUserIdAndStatus(Long userId, CouponStatusEnum status);

}
