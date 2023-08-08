package com.et.movies.repository;

import com.et.movies.entities.Movie;
import com.et.movies.entities.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ScreeningRepository extends JpaRepository<Screening,Long> {
    List<Screening> findByMovie(Movie movie);
    List<Screening> findByIsFullTrue();
    List<Screening> findByAvailable_seats(int available_seat);
}
