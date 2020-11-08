package com.bilyoner.assignment.couponapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CouponSelectionEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "coupon_id")
    private CouponEntity coupon;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;

}
