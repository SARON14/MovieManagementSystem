package com.et.movies.dto.response;

import com.et.movies.entities.Movie;
import com.et.movies.entities.Screening;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class ScreeningListDto {
    private List<Movie> movies;
    private List<Screening> screenings;
}
