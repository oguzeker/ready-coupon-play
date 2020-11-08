package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.model.EventCreateRequest;
import com.bilyoner.assignment.couponapi.model.EventDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EventService {

    Page<EventDTO> getAllEvents(int pageIndex, int pageSize);

    EventDTO createEvent(EventCreateRequest eventRequest);

    EventDTO getEventById(Long id);

    void deleteEvent(Long id);

    List<EventEntity> getEventEntitiesByIds(List<Long> eventIds);

}
