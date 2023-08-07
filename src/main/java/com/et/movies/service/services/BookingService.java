package com.et.movies.service.services;

import com.et.movies.constants.ApiMessages;
import com.et.movies.dto.request.BookingRequestDto;
import com.et.movies.dto.response.ResponseDTO;
import com.et.movies.entities.Booking;
import com.et.movies.entities.Screening;
import com.et.movies.entities.User;
import com.et.movies.exception.NotFoundException;
import com.et.movies.repository.BookingRepository;
import com.et.movies.repository.ScreeningRepository;
import com.et.movies.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ScreeningRepository screeningRepository;
    private final UserRepository userRepository;
    private final ApiMessages apiMessages = new ApiMessages();

    public ResponseDTO<?> bookTicket(BookingRequestDto bookingRequestDto) {
        Screening screening = screeningRepository.findById(bookingRequestDto.getScreening_id())
                .orElseThrow(()->new NotFoundException("Screening Not Found"));
        if(screening.getIsFull()){
            return apiMessages.errorMessage("Screening Full");
        }
        User user = userRepository.findById(bookingRequestDto.getUser_id())
                .orElseThrow(()->new NotFoundException("User not found"));
        String ticket = UUID.randomUUID().toString();
        Booking booking = bookingRepository.save(Booking.builder()
                        .screening(screening)
                        .user(user)
                        .isBooked(true)
                        .isActive(true)
                        .bookingTime(LocalTime.now())
                        .ticket(ticket)
                .build());
        screening.setAvailable_seats(screening.getAvailable_seats() - 1);
        screeningRepository.save(screening);
        return apiMessages.successMessageWithData(booking);
    }
    public ResponseDTO<?> getBooking() {
        List<Booking> booking = bookingRepository.findAll();
        return apiMessages.successMessageWithListData(booking);
    }
}
