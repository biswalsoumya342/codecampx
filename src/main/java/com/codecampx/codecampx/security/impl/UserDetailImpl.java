package com.codecampx.codecampx.security.impl;

import com.codecampx.codecampx.model.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailImpl implements UserDetails {

    AppUser user;

    public UserDetailImpl(AppUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole()),
        new SimpleGrantedAuthority("ROLE_"+user.getRole())
        );
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    public String getEmail(){
        return user.getEmail();
    }
}
