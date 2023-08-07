package com.et.movies.controller;

import com.et.movies.constants.Endpoints;
import com.et.movies.dto.request.BookingRequestDto;
import com.et.movies.dto.response.ResponseDTO;
import com.et.movies.service.services.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    @PostMapping(value = Endpoints.BOOK_TICKET, consumes = JSON, produces = JSON)
    public ResponseDTO<?> bookTicket(@RequestBody @Valid BookingRequestDto bookingRequestDto) {
        return bookingService.bookTicket(bookingRequestDto);
    }
    @GetMapping(value = Endpoints.GET_All_BOOKING,produces = JSON)
    public ResponseDTO<?> getBooking(){
        return bookingService.getBooking();
    }
}
