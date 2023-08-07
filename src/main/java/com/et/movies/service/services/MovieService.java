package com.et.movies.service.services;

import com.et.movies.constants.ApiMessages;
import com.et.movies.dto.request.MovieRequestDto;
import com.et.movies.dto.response.ResponseDTO;
import com.et.movies.entities.Movie;
import com.et.movies.exception.NotFoundException;
import com.et.movies.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final ApiMessages apiMessages = new ApiMessages();
    public ResponseDTO<?> createMovie(MovieRequestDto movieRequestDto) {
        Movie movie = movieRepository.save(Movie.builder()
                        .title(movieRequestDto.getTitle())
                        .duration(movieRequestDto.getDuration())
                        .genre(movieRequestDto.getGenre())
                        .description(movieRequestDto.getDescription())
                        .rating(movieRequestDto.getRating())
                        .build());
        return apiMessages.successMessageWithData(movie);
    }
    public ResponseDTO<?> updateMovie(Long movie_id, MovieRequestDto movieRequestDto) {
        Movie movie = movieRepository.findById(movie_id)
                .orElseThrow(()->new NotFoundException("Movie Not Found"));
        movie.setTitle(movieRequestDto.getTitle());
        movie.setDuration(movieRequestDto.getDuration());
        movie.setGenre(movieRequestDto.getGenre());
        movie.setDescription(movieRequestDto.getDescription());
        movie.setRating(movieRequestDto.getRating());
        movieRepository.save(movie);
        return apiMessages.successMessageWithData(movie);
    }
    public ResponseDTO<?> deleteMovie(Long movie_id) {
        Movie movie = movieRepository.findById(movie_id)
                .orElseThrow(()->new NotFoundException("Movie Not Found"));
        movieRepository.delete(movie);
        return apiMessages.successMessageWithMessageOnly("Movie deleted successfully");
    }

    public ResponseDTO<?> getMovies() {
        List<Movie> movie = movieRepository.findAll();
        return apiMessages.successMessageWithListData(movie);
    }
}
