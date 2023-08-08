package com.et.movies.controller;

import com.et.movies.constants.Endpoints;
import com.et.movies.dto.request.UserRequestDto;
import com.et.movies.dto.response.ResponseDTO;
import com.et.movies.service.services.UserService;
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
public class UserController {
    @Autowired
    private final UserService userService;
    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    @PostMapping(value = Endpoints.UPDATE_USER,consumes = JSON,produces = JSON)
    public ResponseDTO<?> updateUser(@PathVariable Integer user_id,@RequestBody @Valid UserRequestDto userRequestDto ){
        return userService.updateUser(user_id,userRequestDto);
    }
    @DeleteMapping(value = Endpoints.DELETE_USER)
    public ResponseDTO<?> deleteUser(@PathVariable Integer user_id){
        return userService.deleteUser(user_id);
    }
    @GetMapping(value = Endpoints.GET_All_USERS,produces = JSON)
    public ResponseDTO<?> getUsers(){
        return userService.getUsers();
    }

}
