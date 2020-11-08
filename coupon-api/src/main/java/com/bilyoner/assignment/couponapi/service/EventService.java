package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.model.EventCreateRequest;
import com.bilyoner.assignment.couponapi.model.EventDTO;
import org.springframework.data.domain.Page;

public interface EventService {

    Page<EventDTO> getAllEvents(int pageIndex, int pageSize);

    EventDTO createEvent(EventCreateRequest eventRequest);

    EventDTO getEventById(Long id);

    void deleteEvent(Long id);

}
