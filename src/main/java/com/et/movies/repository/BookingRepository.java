package com.et.movies.repository;

import com.et.movies.entities.Booking;
import com.et.movies.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    Optional<Booking> findByUser(User user);
}
