package com.et.movies;

import com.et.movies.constants.ResponseCodes;
import com.et.movies.dto.request.BookingRequestDto;
import com.et.movies.dto.response.ResponseDTO;
import com.et.movies.entities.Booking;
import com.et.movies.entities.Screening;
import com.et.movies.entities.User;
import com.et.movies.exception.NotFoundException;
import com.et.movies.repository.BookingRepository;
import com.et.movies.repository.ScreeningRepository;
import com.et.movies.repository.UserRepository;
import com.et.movies.service.services.BookingService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class BookingServiceTest {

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBookTicket_SuccessfulBooking() {
        // Arrange
        BookingRequestDto bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setScreening_id(1L);
        bookingRequestDto.setUser_id(1);

        Screening screening = new Screening();
        screening.setIsFull(false);
        screening.setAvailable_seats(5);

        User user = new User();

        Booking booking = new Booking();
        booking.setTicket(UUID.randomUUID().toString());

        when(screeningRepository.findById(bookingRequestDto.getScreening_id())).thenReturn(Optional.of(screening));
        when(userRepository.findById(bookingRequestDto.getUser_id())).thenReturn(Optional.of(user));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Act
        ResponseDTO<?> response = bookingService.bookTicket(bookingRequestDto);

        // Assert
        Assert.assertNotNull(response);
        Assert.assertEquals(ResponseCodes.SUCCESS_CODE, response.getStatus());
        Assert.assertEquals(booking, response.getData());

        verify(screeningRepository, times(1)).findById(bookingRequestDto.getScreening_id());
        verify(userRepository, times(1)).findById(bookingRequestDto.getUser_id());
        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(screeningRepository, times(1)).save(screening);
    }

    @Test
    public void testBookTicket_ScreeningFullError() {
        // Arrange
        BookingRequestDto bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setScreening_id(1L);
        bookingRequestDto.setUser_id(1);

        Screening screening = new Screening();
        screening.setIsFull(true);

        when(screeningRepository.findById(bookingRequestDto.getScreening_id())).thenReturn(Optional.of(screening));

        // Act
        ResponseDTO<?> response = bookingService.bookTicket(bookingRequestDto);

        // Assert
        Assert.assertNotNull(response);
        Assert.assertEquals(ResponseCodes.FAILURE_CODE, response.getStatus());
        Assert.assertEquals("Screening Full", response.getMessage());

        verify(screeningRepository, times(1)).findById(bookingRequestDto.getScreening_id());
        verify(userRepository, never()).findById(anyInt());
        verify(bookingRepository, never()).save(any(Booking.class));
        verify(screeningRepository, never()).save(any(Screening.class));
    }

    @Test(expected = ChangeSetPersister.NotFoundException.class)
    public void testBookTicket_ScreeningNotFound() {
        // Arrange
        BookingRequestDto bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setScreening_id(1L);
        bookingRequestDto.setUser_id(1);

        when(screeningRepository.findById(bookingRequestDto.getScreening_id())).thenReturn(Optional.empty());

        // Act
        bookingService.bookTicket(bookingRequestDto);

        // NotFoundException should be thrown
        throw new NotFoundException("Screening Not Found");
    }

    @Test(expected = NotFoundException.class)
    public void testBookTicket_UserNotFound() {
        // Arrange
        BookingRequestDto bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setScreening_id(1L);
        bookingRequestDto.setUser_id(1);

        Screening screening = new Screening();

        when(screeningRepository.findById(bookingRequestDto.getScreening_id())).thenReturn(Optional.of(screening));
        when(userRepository.findById(bookingRequestDto.getUser_id())).thenReturn(Optional.empty());

        // Act
        bookingService.bookTicket(bookingRequestDto);

        // NotFoundException should be thrown
        throw new NotFoundException("User Not Found");
    }
}