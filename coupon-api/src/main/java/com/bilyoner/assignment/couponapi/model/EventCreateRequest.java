package com.bilyoner.assignment.couponapi.model;

import com.bilyoner.assignment.couponapi.model.enums.EventTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventCreateRequest {

    @NotNull
    @ApiModelProperty(value = "Name of the event entity", example = "Nankatsu-Meiwa", position = 1)
    private String name;

    @NotNull
    @ApiModelProperty(value = "Minimum event count required for a coupon to contain before selecting this event",
            example = "3", position = 2)
    private Integer mbs;

    @NotNull
    @ApiModelProperty(value = "Type of the event entity", example = "FOOTBALL", position = 3)
    private EventTypeEnum type;

    @NotNull
    @ApiModelProperty(value = "Date on which the event is planned to take place",
            example = "2020-11-07T23:00:00", position = 4)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

}
