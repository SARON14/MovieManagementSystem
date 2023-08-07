package com.et.movies.service.services;

import com.et.movies.constants.ApiMessages;
import com.et.movies.dto.request.ScreeningRequestDto;
import com.et.movies.dto.response.ResponseDTO;
import com.et.movies.dto.response.ScreeningListDto;
import com.et.movies.entities.Movie;
import com.et.movies.entities.Screening;
import com.et.movies.exception.NotFoundException;
import com.et.movies.repository.MovieRepository;
import com.et.movies.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final ApiMessages apiMessages = new ApiMessages();
    public ResponseDTO<?> createScreening(ScreeningRequestDto screeningRequestDto) {
        Movie movie = movieRepository.findById(screeningRequestDto.getMovie_id())
                .orElseThrow(()->new NotFoundException("Movie Not Found"));
        Screening screening = screeningRepository.save(Screening.builder()
                        .movie(movie)
                        .date(screeningRequestDto.getDate())
                        .startTime(screeningRequestDto.getStartTime())
                        .endTime(screeningRequestDto.getEndTime())
                        .isFull(false)
                        .price(screeningRequestDto.getPrice())
                        .available_seats(screeningRequestDto.getAvailable_seats())
                        .build());
        return apiMessages.successMessageWithData(screening);
    }

    public ResponseDTO<?> updateScreening(Long screening_id, ScreeningRequestDto screeningRequestDto) {
        Screening screening= screeningRepository.findById(screening_id)
                .orElseThrow(()->new NotFoundException("Screening Not Found"));
        Movie movie = movieRepository.findById(screeningRequestDto.getMovie_id())
                .orElseThrow(()->new NotFoundException("Movie Not Found"));
        screening.setMovie(movie);
        screening.setDate(screeningRequestDto.getDate());
        screening.setStartTime(screeningRequestDto.getStartTime());
        screening.setEndTime(screeningRequestDto.getEndTime());
        screening.setAvailable_seats(screeningRequestDto.getAvailable_seats());
        screening.setPrice(screeningRequestDto.getPrice());
        screening.setIsFull(false);
        return apiMessages.successMessageWithData(screeningRepository.save(screening));
    }

    public ResponseDTO<?> deleteScreening(Long screening_id) {
        Screening screening= screeningRepository.findById(screening_id)
                .orElseThrow(()->new NotFoundException("Screening Not Found"));
        screeningRepository.delete(screening);
        return apiMessages.successMessageWithMessageOnly("Screening deleted successfully");
    }

    public ResponseDTO<?> getScreening(String movie_title, String genre) {
        if(genre != null && movie_title == null) {
            ScreeningListDto screeningListDtos =  new ScreeningListDto();
            List<Movie> movieList = movieRepository.findAllByGenre(genre);
            movieList.forEach(movie -> {
                List<Screening> screeningByGenre =  screeningRepository.findByMovie(movie);
                screeningListDtos.setScreenings(screeningByGenre);
                screeningListDtos.setMovies(movieList);
            });
            return apiMessages.successMessageWithData(screeningListDtos);
        }
        if(movie_title != null && genre == null){
            List<Screening> screeningByTitle = screeningRepository.findByMovie(movieRepository.findByTitle(movie_title));

            return apiMessages.successMessageWithListData(screeningByTitle);
        }else {
            List<Screening> screening = screeningRepository.findAll();
            return apiMessages.successMessageWithListData(screening);
        }
    }
}
