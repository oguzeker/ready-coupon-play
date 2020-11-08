package com.bilyoner.assignment.couponapi.service.impl;

import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.model.enums.EventTypeEnum;
import com.bilyoner.assignment.couponapi.repository.EventRepository;
import com.bilyoner.assignment.couponapi.service.StartupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class StartupServiceImpl implements StartupService {

    private final EventRepository eventRepository;

    /**
     * Populates sample event information to event table.
     */
    @PostConstruct
    private void initDb() {
        LocalDateTime now = LocalDateTime.now()
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        if (eventRepository.count() <= 0) {
            createFootballEvents(now);
            createBasketballEvents(now);
            createTennisEvents(now);
        }
    }

    private void createFootballEvents(LocalDateTime time) {
        EventEntity football1 = EventEntity.builder()
                .name("Beşiktaş-Fenerbahçe")
                .mbs(3)
                .type(EventTypeEnum.FOOTBALL)
                .eventDate(time.plusHours(1))
                .build();
        EventEntity football2 = EventEntity.builder()
                .name("Göztepe-Alanyaspor")
                .mbs(2)
                .type(EventTypeEnum.FOOTBALL)
                .eventDate(time.plusHours(2))
                .build();
        EventEntity football3 = EventEntity.builder()
                .name("Antalyaspor-Kayserispor")
                .mbs(2)
                .type(EventTypeEnum.FOOTBALL)
                .eventDate(time.plusHours(2))
                .build();
        EventEntity football4 = EventEntity.builder()
                .name("Hatayspor-Erzurumspor")
                .mbs(2)
                .type(EventTypeEnum.FOOTBALL)
                .eventDate(time.minusHours(2))
                .build();
        EventEntity football5 = EventEntity.builder()
                .name("Sivasspor-Denizlispor")
                .mbs(2)
                .type(EventTypeEnum.FOOTBALL)
                .eventDate(time.minusHours(2))
                .build();
        EventEntity football6 = EventEntity.builder()
                .name("Konyaspor-Ankaragücü")
                .mbs(1)
                .type(EventTypeEnum.FOOTBALL)
                .eventDate(time.minusHours(3))
                .build();
        EventEntity football7 = EventEntity.builder()
                .name("Kayserispor-Rizespor")
                .mbs(1)
                .type(EventTypeEnum.FOOTBALL)
                .eventDate(time.plusHours(3))
                .build();
        EventEntity football8 = EventEntity.builder()
                .name("Trabzonspor-Konyaspor")
                .mbs(1)
                .type(EventTypeEnum.FOOTBALL)
                .eventDate(time.plusHours(3))
                .build();

        eventRepository.saveAll(Arrays.asList(football1, football2, football3, football4,
                football5, football6, football7, football8));
    }

    private void createBasketballEvents(LocalDateTime time) {
        EventEntity basketball1 = EventEntity.builder()
                .name("Anadolu Efes-Galatasaray")
                .mbs(3)
                .type(EventTypeEnum.BASKETBALL)
                .eventDate(time.minusHours(1))
                .build();
        EventEntity basketball2 = EventEntity.builder()
                .name("Türk Telekom-Darüşşafaka")
                .mbs(2)
                .type(EventTypeEnum.BASKETBALL)
                .eventDate(time.plusHours(2))
                .build();
        EventEntity basketball3 = EventEntity.builder()
                .name("Bursaspor-Beşiktaş")
                .mbs(2)
                .type(EventTypeEnum.BASKETBALL)
                .eventDate(time.minusHours(2))
                .build();
        EventEntity basketball4 = EventEntity.builder()
                .name("Fenerbahçe-Ormanspor")
                .mbs(2)
                .type(EventTypeEnum.BASKETBALL)
                .eventDate(time.plusHours(2))
                .build();
        EventEntity basketball5 = EventEntity.builder()
                .name("Afyon-Gaziantep")
                .mbs(1)
                .type(EventTypeEnum.BASKETBALL)
                .eventDate(time.plusHours(2))
                .build();

        eventRepository.saveAll(Arrays.asList(basketball1, basketball2, basketball3, basketball4,
                basketball5));
    }

    private void createTennisEvents(LocalDateTime time) {
        EventEntity tennis1 = EventEntity.builder()
                .name("Başak-Berfu")
                .mbs(1)
                .type(EventTypeEnum.TENNIS)
                .eventDate(time.minusHours(1))
                .build();
        EventEntity tennis2 = EventEntity.builder()
                .name("Pemra-Cansu")
                .mbs(1)
                .type(EventTypeEnum.TENNIS)
                .eventDate(time.plusHours(1))
                .build();

        eventRepository.saveAll(Arrays.asList(tennis1, tennis2));
    }
}
