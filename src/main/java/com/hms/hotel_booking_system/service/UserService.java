package com.hms.hotel_booking_system.service;

import com.hms.hotel_booking_system.entity.AppUser;
import com.hms.hotel_booking_system.exception.InvalidCredentialsException;
import com.hms.hotel_booking_system.exception.ResourceAlreadyExistException;
import com.hms.hotel_booking_system.exception.ResourceNotFoundException;
import com.hms.hotel_booking_system.payload.AppUserDto;
import com.hms.hotel_booking_system.payload.LoginDto;
import com.hms.hotel_booking_system.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private AppUserRepository appUserRepository;

    private ModelMapper modelMapper;

    private JWTService jwtService;

    public UserService(AppUserRepository appUserRepository, ModelMapper modelMapper, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    public AppUserDto createUser(AppUser user) throws ResourceAlreadyExistException{
        Optional<AppUser> opUsername = appUserRepository.findByUsername(user.getUsername());

        if(opUsername.isPresent()){
            throw new ResourceAlreadyExistException("Username already exists");
        }

        Optional<AppUser> opEmail = appUserRepository.findByEmail(user.getEmail());

        if(opEmail.isPresent()){
            throw new ResourceAlreadyExistException("Email already exists");
        }

        String encryptedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5));
        user.setPassword(encryptedPassword);
        user.setRole("ROLE_USER");
        AppUser savedUser = appUserRepository.save(user);
        AppUserDto dto = modelMapper.map(savedUser, AppUserDto.class);
        return dto;
    }

    public AppUserDto createPropertyOwnerUser(AppUser user) {
        Optional<AppUser> opUsername = appUserRepository.findByUsername(user.getUsername());

        if(opUsername.isPresent()){
            throw new ResourceAlreadyExistException("Username already exists");
        }

        Optional<AppUser> opEmail = appUserRepository.findByEmail(user.getEmail());

        if(opEmail.isPresent()){
            throw new ResourceAlreadyExistException("Email already exists");
        }

        String encryptedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5));
        user.setPassword(encryptedPassword);
        user.setRole("ROLE_OWNER");
        AppUser savedUser = appUserRepository.save(user);
        AppUserDto dto = modelMapper.map(savedUser, AppUserDto.class);
        return dto;
    }

    public String verifyLogin(LoginDto dto){
        Optional<AppUser> opUser = appUserRepository.findByUsername(dto.getUsername());
        if(opUser.isPresent()){
            AppUser appUser = opUser.get();
            if(BCrypt.checkpw(dto.getPassword(), appUser.getPassword())){
                //Generate Token
                String token = jwtService.generateToken(appUser.getUsername());
                return token;
            }
            else{
                throw new InvalidCredentialsException("Invalid Password. Please try again");
            }
        }else {
            throw new InvalidCredentialsException("Invalid Username. Please try again");
        }
    }


}
