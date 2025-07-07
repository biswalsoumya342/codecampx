package com.codecampx.codecampx.service;

import com.codecampx.codecampx.payload.appuser.AppUserSignUpDto;
import com.codecampx.codecampx.payload.appuser.AppUserUpdateDto;
import com.codecampx.codecampx.payload.appuser.LoginDto;

public interface AppUserService {
    public boolean signup(AppUserSignUpDto userDto);
    public String login(LoginDto loginDto);
    public boolean update(String userName, AppUserUpdateDto userDto);
    public boolean delete(String userName);
}
