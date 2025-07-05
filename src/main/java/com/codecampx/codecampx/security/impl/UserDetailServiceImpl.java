package com.codecampx.codecampx.security.impl;

import com.codecampx.codecampx.exception.ResorceNotFoundException;
import com.codecampx.codecampx.model.AppUser;
import com.codecampx.codecampx.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private AppUserRepository repo;

    public UserDetailServiceImpl(AppUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repo.findByUserName(username).orElseThrow(()->new ResorceNotFoundException("User","UserName",username));
        return new UserDetailImpl(user);
    }
}
