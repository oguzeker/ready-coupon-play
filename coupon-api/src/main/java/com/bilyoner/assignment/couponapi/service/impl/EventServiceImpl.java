package com.bilyoner.assignment.couponapi.service.impl;

import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.exception.CouponApiException;
import com.bilyoner.assignment.couponapi.exception.ErrorCodeEnum;
import com.bilyoner.assignment.couponapi.model.EventCreateRequest;
import com.bilyoner.assignment.couponapi.model.EventDTO;
import com.bilyoner.assignment.couponapi.repository.EventRepository;
import com.bilyoner.assignment.couponapi.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ModelMapper mapper;

    public Page<EventDTO> getAllEvents(int pageIndex, int pageSize) {
        log.info("getAllEvents-begin {} {}", kv("pageIndex", pageIndex), kv("pageSize", pageSize));

        Page<EventDTO> response = eventRepository.findAll(PageRequest.of(pageIndex, pageSize))
                .map(eventEntity -> mapper.map(eventEntity, EventDTO.class));

        log.info("getAllEvents-end {}", kv("response", response));
        return response;
    }

    public EventDTO createEvent(EventCreateRequest eventRequest) {
        log.info("createEvent-begin {}", kv("eventRequest", eventRequest));

        EventDTO response = mapper.map(eventRequest, EventDTO.class);
        EventEntity entity = eventRepository.save(mapper.map(eventRequest, EventEntity.class));

        response.setId(entity.getId());
        log.info("createEvent-end {}", kv("response", response));
        return response;
    }

    public EventDTO getEventById(Long id) {
        log.info("getEventById-begin {}", kv("id", id));

        EventEntity entity = eventRepository.findById(id).orElseThrow(() ->
                new CouponApiException(ErrorCodeEnum.ENTITY_NOT_FOUND_VALUE, id));

        EventDTO response = mapper.map(entity, EventDTO.class);
        log.info("getEventById-end {}", kv("response", response));
        return response;
    }

    public void deleteEvent(Long id) {
        log.info("deleteEvent-begin {}", kv("id", id));

        eventRepository.deleteById(id);

        log.info("deleteEvent-end");
    }

    protected List<EventEntity> getEventEntitiesByIds(List<Long> eventIds) {
        log.info("getEventEntitiesByIds-begin {}", kv("eventIds", eventIds));

        List<EventEntity> response = eventRepository.findByIdIn(eventIds);

        log.info("getEventEntitiesByIds-end {}", kv("response", response));
        return response;
    }

}
