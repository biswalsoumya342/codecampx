package com.codecampx.codecampx.service.impl;

import com.codecampx.codecampx.model.AppUser;
import com.codecampx.codecampx.payload.AppUserDto;
import com.codecampx.codecampx.payload.LoginDto;
import com.codecampx.codecampx.repository.AppUserRepository;
import com.codecampx.codecampx.security.JwtService;
import com.codecampx.codecampx.service.AppUserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository repo;
    private ModelMapper mapper;
    private AuthenticationManager manager;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public AppUserServiceImpl(AppUserRepository repo, ModelMapper mapper, AuthenticationManager manager, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repo = repo;
        this.mapper = mapper;
        this.manager = manager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public void signup(AppUserDto userDto) {
        AppUser user = mapper.map(userDto, AppUser.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole("ADMIN");
        repo.save(user);
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = manager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getUserName(),loginDto.getPassword()
                        ));
        return jwtService.generateToken(authentication.getName());
    }
}
