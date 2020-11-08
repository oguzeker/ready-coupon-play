package com.bilyoner.assignment.couponapi;

import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.model.enums.EventTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = CouponApiApplication.class)
public class CouponApiApplicationTest {

    private static final String EVENTS = "/events";
    private static final String HTTP_LOCALHOST = "http://localhost:";
    private static final int MBS = 3;
    private static final long ID = 1L;
    private static final int YEAR = 2020;
    private static final String EVENT_NAME = "Beşiktaş-Fenerbahçe";

    private static ObjectMapper mapper;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public static void init() throws Exception {
        mapper = Jackson2ObjectMapperBuilder
                .json()
                .modules(new JavaTimeModule(), new SimpleModule())
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();


        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }


    @Test
    @SuppressWarnings({"StringBufferReplaceableByString", "SameParameterValue"})
    public void getEventById_Test() throws Exception {
        String path = "/1";
        String url = new StringBuilder(createMainURL(EVENTS))
                .append(path).toString();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assert HttpStatus.OK.value() == response.getStatusCodeValue();

        EventEntity eventEntity = mapper.readValue(response.getBody(), new TypeReference<>(){});

        assert eventEntity.getName().equals(EVENT_NAME);
        assert eventEntity.getMbs() == MBS;
        assert eventEntity.getType() == EventTypeEnum.FOOTBALL;
        assert eventEntity.getEventDate().getYear() == YEAR;
        assert eventEntity.getId() == ID;
    }

    @SuppressWarnings({"StringBufferReplaceableByString", "SameParameterValue"})
    private String createMainURL(String uri) {
        return new StringBuilder(HTTP_LOCALHOST)
                .append(port)
                .append(uri).toString();
    }

}
