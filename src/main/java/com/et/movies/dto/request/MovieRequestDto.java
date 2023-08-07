package com.et.movies.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieRequestDto {
    private String title;
    private String duration;
    private String genre;
    private String description;
    private Integer rating;
}
