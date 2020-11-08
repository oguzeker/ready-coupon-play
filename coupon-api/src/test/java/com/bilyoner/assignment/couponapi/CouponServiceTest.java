package com.bilyoner.assignment.couponapi;

import com.bilyoner.assignment.couponapi.configuration.properties.CouponApiProperties;
import com.bilyoner.assignment.couponapi.entity.CouponEntity;
import com.bilyoner.assignment.couponapi.entity.CouponSelectionEntity;
import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.model.CouponCreateRequest;
import com.bilyoner.assignment.couponapi.model.CouponDTO;
import com.bilyoner.assignment.couponapi.model.enums.CouponStatusEnum;
import com.bilyoner.assignment.couponapi.model.enums.EventTypeEnum;
import com.bilyoner.assignment.couponapi.repository.CouponRepository;
import com.bilyoner.assignment.couponapi.repository.CouponSelectionRepository;
import com.bilyoner.assignment.couponapi.service.EventService;
import com.bilyoner.assignment.couponapi.service.impl.CouponServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@CoreTest
public class CouponServiceTest extends TestBase {

    @InjectMocks
    CouponServiceImpl subject;
    @Mock
    CouponRepository couponRepository;
    @Mock
    CouponSelectionRepository couponSelectionRepository;
    @Mock
    EventService eventService;
    @Mock
    ModelMapper mapper;
    @Mock
    CouponApiProperties properties;
    @Captor
    ArgumentCaptor<CouponEntity> captor;

    @Test
    public void createCoupon_Test() {
        CouponCreateRequest request = createCouponCreateRequest();
        List<Long> eventIds = request.getEventIds();

        EventEntity eventEntity = createEventEntity();
        List<EventEntity> events = Collections.singletonList(eventEntity);

        when(eventService.getEventEntitiesByIds(eventIds)).thenReturn(events);

        CouponEntity couponEntity = createCouponEntity();
        when(couponRepository.save(any(CouponEntity.class))).thenReturn(couponEntity);

        when(properties.getEventTypesToCheck()).thenReturn(Collections.singletonList(EventTypeEnum.TENNIS));
        when(properties.getEventTypeCombinationCount()).thenReturn(EVENT_TYPE_COMBINATION_COUNT);
        when(properties.getCouponCost()).thenReturn(COST);

        CouponSelectionEntity couponSelectionEntity = createCouponSelectionEntity(eventEntity, couponEntity);
        when(couponSelectionRepository.save(any(CouponSelectionEntity.class))).thenReturn(couponSelectionEntity);

        CouponDTO couponDTO = createCouponDTO();
        when(mapper.map(couponEntity, CouponDTO.class)).thenReturn(couponDTO);

        CouponDTO subjectResponse = subject.createCoupon(request);

        assert subjectResponse != null;
        assert !subjectResponse.getEventIds().isEmpty();
        assert subjectResponse.getEventIds().size() == eventIds.size();
        assert subjectResponse.getId() == ID;
        assert subjectResponse.getCost().equals(COST);
        assert subjectResponse.getStatus() == CouponStatusEnum.CREATED;
        assert subjectResponse.getUserId() == null;
        assert subjectResponse.getPlayDate() == null;

        verify(couponRepository).save(captor.capture());
        CouponEntity capturedCoupon = captor.getValue();

        assert capturedCoupon != null;
        assert capturedCoupon.getId() == null;
        assert capturedCoupon.getCost().equals(COST);
        assert capturedCoupon.getStatus() == CouponStatusEnum.CREATED;
        assert capturedCoupon.getUserId() == null;
        assert capturedCoupon.getPlayDate() == null;

        verify(mapper, times(1)).map(couponEntity, CouponDTO.class);
        verify(couponRepository, times(1)).save(any(CouponEntity.class));
        verify(couponSelectionRepository, times(1)).save(any(CouponSelectionEntity.class));

        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(couponRepository);
        verifyNoMoreInteractions(couponSelectionRepository);
    }
}
