package com.bilyoner.assignment.couponapi.controller;

import com.bilyoner.assignment.couponapi.configuration.SwaggerConfiguration;
import com.bilyoner.assignment.couponapi.model.EventCreateRequest;
import com.bilyoner.assignment.couponapi.model.EventDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Api(tags = {
        SwaggerConfiguration.TAG_EVENT
})
public interface EventController {

    @ApiOperation(value = "Get All Events", notes = "This endpoint retrieves all events (with Pagination)")
    ResponseEntity<Page<EventDTO>> getAllEvents(
            @ApiParam(value = "Index of the requested page of bulk results", example = "0") @RequestParam int pageIndex,
            @ApiParam(value = "Size of the requested page of bulk results", example = "10") @RequestParam int pageSize);

    @ApiOperation(value = "Create Event", notes = "This endpoint event entity")
    ResponseEntity<EventDTO> createEvent(@ApiParam(value = "Details of the event entity to create")
                                         @RequestBody @Valid EventCreateRequest eventRequest);

    @ApiOperation(value = "Get Event By Id", notes = "This endpoint retrieves event entity matching the given id")
    ResponseEntity<EventDTO> getEventById(@ApiParam(value = "Id of the event entity") @PathVariable Long id);

    @ApiOperation(value = "Delete Event", notes = "This endpoint deletes event entity with the given id")
    ResponseEntity<String> deleteEvent(@ApiParam(value = "Id of the event entity to delete", example = "1")
                                       @PathVariable Long id);

}
