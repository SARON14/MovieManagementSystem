package com.et.movies.controller;

import com.et.movies.constants.Endpoints;
import com.et.movies.dto.request.MovieRequestDto;
import com.et.movies.dto.response.ResponseDTO;
import com.et.movies.service.services.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MovieController {
 @Autowired
 private final MovieService movieService;
 private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

 @PostMapping(value = Endpoints.REGISTER_MOVIE, consumes = JSON, produces = JSON)
 public ResponseDTO<?> createMovie(@RequestBody @Valid MovieRequestDto movieRequestDto) {
  return movieService.createMovie(movieRequestDto);
 }
 @PostMapping(value = Endpoints.UPDATE_MOVIE,consumes = JSON)
 public ResponseDTO<?> updateMovie(@PathVariable Long movie_id,@RequestBody @Valid MovieRequestDto movieRequestDto){
  return movieService.updateMovie(movie_id,movieRequestDto);
 }
 @DeleteMapping(value = Endpoints.DELETE_MOVIE)
 public ResponseDTO<?>  deleteMovie(@PathVariable Long movie_id){
  return movieService.deleteMovie(movie_id);
 }
 @GetMapping(value = Endpoints.GET_All_MOVIES,produces = JSON)
 public ResponseDTO<?> getMovies(){
  return movieService.getMovies();
 }

}
