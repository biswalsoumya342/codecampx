package com.codecampx.codecampx.repository;

import com.codecampx.codecampx.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,String> {
    public Optional<AppUser> findByUserName(String userName);
}
