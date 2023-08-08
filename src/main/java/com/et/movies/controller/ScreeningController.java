package com.et.movies.controller;

import com.et.movies.constants.Endpoints;
import com.et.movies.dto.request.ScreeningRequestDto;
import com.et.movies.dto.response.ResponseDTO;
import com.et.movies.service.services.ScreeningService;
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
public class ScreeningController {
    @Autowired
    private final ScreeningService screeningService;

    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

  @PostMapping(value = Endpoints.CREATE_SCREENING,produces = JSON,consumes = JSON)
  public ResponseDTO<?> createScreening(@RequestBody @Valid ScreeningRequestDto screeningRequestDto){
      return screeningService.createScreening(screeningRequestDto);
  }
  @PostMapping(value = Endpoints.UPDATE_SCREENING,produces = JSON,consumes = JSON)
    public ResponseDTO<?> updateScreening(@PathVariable Long screening_id,@RequestBody @Valid ScreeningRequestDto screeningRequestDto){
      return screeningService.updateScreening(screening_id ,screeningRequestDto);
  }
 @PostMapping(value = Endpoints.DELETE_SCREENING)
    public ResponseDTO<?> deleteScreening(@PathVariable Long screening_id){
      return screeningService.deleteScreening(screening_id);
 }
 @GetMapping(value = Endpoints.GET_All_SCREENING,produces = JSON)
    public ResponseDTO<?> getScreening (@RequestParam(required = false) String movie_title,
                                        @RequestParam(required = false)String genre){
      return screeningService.getScreening(movie_title,genre);
 }
}
