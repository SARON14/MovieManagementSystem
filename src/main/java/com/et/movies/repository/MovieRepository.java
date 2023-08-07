package com.et.movies.repository;

import com.et.movies.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    List<Movie> findAllByGenre(String genre);
    Movie findByTitle(String title);
}
