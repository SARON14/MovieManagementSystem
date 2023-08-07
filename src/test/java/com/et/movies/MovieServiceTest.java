package com.et.movies;

import com.et.movies.constants.ResponseCodes;
import com.et.movies.dto.request.MovieRequestDto;
import com.et.movies.dto.response.ResponseDTO;
import com.et.movies.entities.Movie;
import com.et.movies.repository.MovieRepository;
import com.et.movies.service.services.MovieService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateMovie_Success() {
        // Arrange
        MovieRequestDto movieRequestDto = new MovieRequestDto();
        movieRequestDto.setTitle("Movie Title");
        movieRequestDto.setDuration("120 min");
        movieRequestDto.setGenre("Action");
        movieRequestDto.setDescription("Movie description");
        movieRequestDto.setRating(4);

        Movie movie = new Movie();
        movie.setTitle(movieRequestDto.getTitle());
        movie.setDuration(movieRequestDto.getDuration());
        movie.setGenre(movieRequestDto.getGenre());
        movie.setDescription(movieRequestDto.getDescription());
        movie.setRating(movieRequestDto.getRating());

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        // Act
        ResponseDTO<?> response = movieService.createMovie(movieRequestDto);

        // Assert
        Assert.assertNotNull(response);
        Assert.assertEquals(ResponseCodes.SUCCESS_CODE, response.getStatus());
        Assert.assertEquals(movie, response.getData());

        verify(movieRepository, times(1)).save(any(Movie.class));
    }
}