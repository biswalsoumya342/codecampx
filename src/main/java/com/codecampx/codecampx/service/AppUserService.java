package com.codecampx.codecampx.service;

import com.codecampx.codecampx.payload.ForgetPasswordPasswordDto;
import com.codecampx.codecampx.payload.PasswordReset;
import com.codecampx.codecampx.payload.appuser.AppUserSignUpDto;
import com.codecampx.codecampx.payload.appuser.AppUserUpdateDto;
import com.codecampx.codecampx.payload.appuser.LoginDto;

public interface AppUserService {
    public boolean signup(AppUserSignUpDto userDto);
    public String login(LoginDto loginDto);
    public boolean update(AppUserUpdateDto userDto);
    public boolean delete();
    public boolean passwordReset(PasswordReset password);
}
