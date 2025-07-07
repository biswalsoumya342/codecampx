package com.codecampx.codecampx.security.impl;

import com.codecampx.codecampx.exception.ResorceNotFoundException;
import com.codecampx.codecampx.model.AppUser;
import com.codecampx.codecampx.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    private AppUserRepository repo;

    public UserDetailServiceImpl(AppUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("UserName: {} Searching On DataBase(UserDetailsService) ",username);

        AppUser user = repo.findByUserName(username).orElseThrow(()->new ResorceNotFoundException("User","UserName",username));

        if (user==null) logger.debug("User Not Found On DataBase with UserName : {}",username);

        else logger.debug("User Found On DataBase with UserName : {}",username);

        return new UserDetailImpl(user);
    }
}
