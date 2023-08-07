package com.et.movies.service.services;

import com.et.movies.constants.ApiMessages;
import com.et.movies.dto.request.UserRequestDto;
import com.et.movies.dto.response.ResponseDTO;
import com.et.movies.entities.Booking;
import com.et.movies.entities.Role;
import com.et.movies.entities.User;
import com.et.movies.exception.NotFoundException;
import com.et.movies.repository.BookingRepository;
import com.et.movies.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ApiMessages apiMessages = new ApiMessages();

    public ResponseDTO<?> updateUser(Integer user_id, UserRequestDto userRequestDto) {
        Optional<User> userRecord = userRepository.findByEmail(userRequestDto.getEmail());
        if (userRecord.isPresent()) {
            log.info("Duplicate Registration! Email already registered with a user.");
            return apiMessages
                    .errorMessage(String.format("Cannot proceed! Email %s already registered.", userRequestDto.getEmail()));
        }
        User user = userRepository.findById(user_id)
                .orElseThrow(()->new NotFoundException("User Not Found"));
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        return apiMessages.successMessageWithData(userRepository.save(user));
    }

    public ResponseDTO<?> deleteUser(Integer user_id) {
        User user= userRepository.findById(user_id)
                .orElseThrow(()->new NotFoundException("User Not Found"));
        Optional<Booking> booking = bookingRepository.findByUser(user);
                   if(booking.isPresent()){
                       return apiMessages.errorMessage("user can't be deleted");
                   }
        userRepository.delete(user);
        return apiMessages.successMessageWithMessageOnly("user Deleted Successfully");
    }
    public ResponseDTO<?> getUsers() {
       List<User> user = userRepository.findAll();
        return apiMessages.successMessageWithListData(user);
    }
}
