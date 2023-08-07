package com.et.movies;

import com.et.movies.constants.ResponseCodes;
import com.et.movies.dto.request.ScreeningRequestDto;
import com.et.movies.dto.response.ResponseDTO;
import com.et.movies.entities.Movie;
import com.et.movies.entities.Screening;
import com.et.movies.exception.NotFoundException;
import com.et.movies.repository.MovieRepository;
import com.et.movies.repository.ScreeningRepository;
import com.et.movies.service.services.ScreeningService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ScreeningServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ScreeningRepository screeningRepository;

    @InjectMocks
    private ScreeningService screeningService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateScreening_Success() {
        // Arrange
        ScreeningRequestDto screeningRequestDto = new ScreeningRequestDto();
        screeningRequestDto.setMovie_id(1L);
        screeningRequestDto.setDate(LocalDate.parse("2023-08-07"));
        screeningRequestDto.setStartTime(LocalTime.parse("14:00"));
        screeningRequestDto.setEndTime(LocalTime.parse("16:00"));
        screeningRequestDto.setPrice(10.0);
        screeningRequestDto.setAvailable_seats(100);

        Movie movie = new Movie();
        movie.setId(screeningRequestDto.getMovie_id());

        Screening screening = new Screening();
        screening.setMovie(movie);
        screening.setDate(screeningRequestDto.getDate());
        screening.setStartTime(screeningRequestDto.getStartTime());
        screening.setEndTime(screeningRequestDto.getEndTime());
        screening.setIsFull(false);
        screening.setPrice(screeningRequestDto.getPrice());
        screening.setAvailable_seats(screeningRequestDto.getAvailable_seats());

        when(movieRepository.findById(screeningRequestDto.getMovie_id())).thenReturn(Optional.of(movie));
        when(screeningRepository.save(any(Screening.class))).thenReturn(screening);

        // Act
        ResponseDTO<?> response = screeningService.createScreening(screeningRequestDto);

        // Assert
        Assert.assertNotNull(response);
        Assert.assertEquals(ResponseCodes.SUCCESS_CODE, response.getStatus());
        Assert.assertEquals(screening, response.getData());

        verify(movieRepository, times(1)).findById(screeningRequestDto.getMovie_id());
        verify(screeningRepository, times(1)).save(any(Screening.class));
    }

    @Test(expected = ChangeSetPersister.NotFoundException.class)
    public void testCreateScreening_MovieNotFound() {
        // Arrange
        ScreeningRequestDto screeningRequestDto = new ScreeningRequestDto();
        screeningRequestDto.setMovie_id(1L);

        when(movieRepository.findById(screeningRequestDto.getMovie_id())).thenReturn(Optional.empty());

        // Act
        screeningService.createScreening(screeningRequestDto);

        // NotFoundException should be thrown
        throw new NotFoundException("Screening Not Found");
    }
}