package com.hms.hotel_booking_system.controller;

import com.hms.hotel_booking_system.entity.AppUser;
import com.hms.hotel_booking_system.exception.ResourceAlreadyExistException;
import com.hms.hotel_booking_system.payload.AppUserDto;
import com.hms.hotel_booking_system.payload.LoginDto;
import com.hms.hotel_booking_system.payload.TokenDto;
import com.hms.hotel_booking_system.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<AppUserDto> createUser(@RequestBody AppUser user) throws ResourceAlreadyExistException {
        AppUserDto dto = userService.createUser(user);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/signup-property-owner")
    public ResponseEntity<AppUserDto> createPropertyOwnerUser(@RequestBody AppUser user) throws ResourceAlreadyExistException {
        AppUserDto dto = userService.createPropertyOwnerUser(user);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto){
        String token = userService.verifyLogin(dto);
        if(token!=null){
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid Username/Password", HttpStatus.FORBIDDEN);
        }
    }
}
