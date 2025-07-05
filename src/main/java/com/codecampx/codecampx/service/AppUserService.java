package com.codecampx.codecampx.service;

import com.codecampx.codecampx.payload.AppUserDto;
import com.codecampx.codecampx.payload.LoginDto;

public interface AppUserService {
    public void signup(AppUserDto userDto);
    public String login(LoginDto loginDto);
}
