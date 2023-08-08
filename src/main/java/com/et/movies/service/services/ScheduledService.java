/*
 * Dev:- Emmanuel Israel
 * codename: @logicrey
 */
package com.et.movies.service.services;

import com.et.movies.entities.Screening;
import com.et.movies.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *
 * @author sharon
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledService {

    private final ScreeningRepository screeningRepository;

    @Scheduled(fixedDelay = 60000)
    public void checkAvailableSeat() {
        List<Screening> screenings = screeningRepository.findByAvailable_seats(0);
        screenings.forEach(screening -> {
            screening.setIsFull(true);
        });
    }
}


