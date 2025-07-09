package com.codecampx.codecampx.service.impl;

import com.codecampx.codecampx.exception.DuplicateResourceEntryException;
import com.codecampx.codecampx.exception.InvalidPasswordException;
import com.codecampx.codecampx.exception.ResorceNotFoundException;
import com.codecampx.codecampx.exception.UnauthorizeAccessException;
import com.codecampx.codecampx.model.AppUser;
import com.codecampx.codecampx.payload.ForgetPasswordPasswordDto;
import com.codecampx.codecampx.payload.PasswordReset;
import com.codecampx.codecampx.payload.appuser.AppUserSignUpDto;
import com.codecampx.codecampx.payload.appuser.AppUserUpdateDto;
import com.codecampx.codecampx.payload.appuser.LoginDto;
import com.codecampx.codecampx.repository.AppUserRepository;
import com.codecampx.codecampx.security.JwtService;
import com.codecampx.codecampx.service.AppUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    private static final Logger logger = LoggerFactory.getLogger(AppUserServiceImpl.class);

    private final AppUserRepository repo;
    private final ModelMapper mapper;
    private final AuthenticationManager manager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AppUserServiceImpl(AppUserRepository repo, ModelMapper mapper, AuthenticationManager manager, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repo = repo;
        this.mapper = mapper;
        this.manager = manager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    //Signup Service
    @Override
    public boolean signup(AppUserSignUpDto userDto) {

        Optional<AppUser> foundUserByUserName = repo.findByUserName(userDto.getUserName());
        Optional<AppUser> foundUserByEmail = repo.findByEmail(userDto.getEmail());
        logger.debug("Searching User With UserName: {} And Email: {}",userDto.getUserName(),userDto.getEmail());
        if (foundUserByUserName.isPresent()){
            logger.error("UserName: {} Already Exist So Signup Failed",userDto.getUserName());
            throw new DuplicateResourceEntryException(userDto.getUserName());
        }
        if (foundUserByEmail.isPresent()){
            logger.error("Email: {} Already Exist So Signup Failed",userDto.getEmail());
            throw new DuplicateResourceEntryException(userDto.getEmail());
        }

        AppUser user = mapper.map(userDto, AppUser.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole("ADMIN");
        AppUser isSaved = repo.save(user);
        return !ObjectUtils.isEmpty(isSaved);
    }

    @Override
    public String login(LoginDto loginDto) {
        logger.debug("Try To Login UserName: {}",loginDto.getUserName());
        Authentication authentication = manager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getUserName(),loginDto.getPassword()
                        ));
        logger.info("User AuthentiCated UserName: {}",loginDto.getUserName());
        return jwtService.generateToken(authentication.getName());
    }



    //User Update Service
    @Override
    public boolean update(AppUserUpdateDto userDto) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        logger.debug("Searching UserData With Username: {}",userName);
        AppUser user = repo.findByUserName(userName).orElseThrow(()-> new ResorceNotFoundException("User","User Name",userName));

        logger.debug("User Found With UserName: {}",user.getUserName());

        //Check If The UserName Is Already Exist Or Not
        if (userDto.getUserName()!=null && !user.getUserName().equals(userDto.getUserName())){
            Optional<AppUser> foundUserByUserName = repo.findByUserName(userDto.getUserName());
            logger.debug("Try Updating UserName");
            if (foundUserByUserName.isPresent() && !foundUserByUserName.get().getId().equals(user.getId())){
                logger.error("UserName: {} Already Exist So Update Failed",foundUserByUserName.get().getUserName());
                throw new DuplicateResourceEntryException(userDto.getUserName());
            }
        }

        //Check If Email Is Already Exist
        if(userDto.getEmail()!=null && !user.getEmail().equals(userDto.getEmail())){
            Optional<AppUser> foundUserByEmail = repo.findByEmail(userDto.getEmail());
            logger.debug("Try Updating Email");
            if (foundUserByEmail.isPresent() && !foundUserByEmail.get().getId().equals(user.getId())){
                logger.error("Email: {} Already Exist So Update Failed",userDto.getEmail());
                throw new DuplicateResourceEntryException(userDto.getEmail());
            }
        }
        if (userDto.getUserName()!=null){
            logger.debug("Setting New Username:{} For User: {}",userDto.getUserName(),userName);
            user.setUserName(userDto.getUserName());
        }
        if (userDto.getEmail()!=null){
            logger.debug("Setting New Email: {} For User: {}",userDto.getEmail(),user.getUserName());
            user.setEmail(userDto.getEmail());
        }

        AppUser isSaved = repo.save(user);
        return !ObjectUtils.isEmpty(isSaved);
    }


    //User Delete Service
    @Override
    public boolean delete() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        logger.debug("Searching User With UserName: {}",userName);
        AppUser user = repo.findByUserName(userName).orElseThrow(
                () -> new ResorceNotFoundException("User","Username",userName)
        );
        repo.deleteById(user.getId());
        AppUser checkAfterDelete = repo.findById(user.getId()).orElse(null);
        if (checkAfterDelete!=null){
            logger.error("User Deletion Failed!");
            return false;
        }else {
            logger.info("User Deletion Successful");
            return true;
        }

    }

    //Password Reset Service
    @Override
    public boolean passwordReset(PasswordReset password) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        logger.debug("Authentication Allowed");

        logger.debug("User Request Reach To password Rest service, UserName {}",userName);
        AppUser user = repo.findByUserName(userName).orElseThrow(
                ()-> new ResorceNotFoundException("User","Username",userName)
        );
        if (passwordEncoder.matches(password.getOldPassword(),user.getPassword())){
            user.setPassword(passwordEncoder.encode(password.getNewPassword()));
            AppUser isSaved = repo.save(user);
            if (!ObjectUtils.isEmpty(isSaved)){
                return true;
            }else{
                return false;
            }
        }
        else {
            throw new InvalidPasswordException("Invalid Password, Please Try Again!");
        }

    }
}
