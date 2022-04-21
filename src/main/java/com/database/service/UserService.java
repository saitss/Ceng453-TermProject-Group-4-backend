package com.database.service;

import com.database.entity.User;
import com.database.repository.UserRepository;
import com.database.security.TokenManager;
import com.database.security.UserDetailService;
import com.mail.Mail;


import com.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;


import java.util.List;
/**
 * User service for user table
 */
@RequiredArgsConstructor
@Service
public class UserService  {

    private final   PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    private UserRepository repository;

    @Autowired
    TokenManager tokenManager;

    @Autowired
    UserDetailService userDetailService;


    /**
     *
     * @return all the users from user table
     */
    public List<User> getUsers() {
        return repository.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public User getUserById(int id) {
        return repository.findById(id);
    }

    public User getUserByName(String name) {
        return repository.findByName(name);
    }

    public String deleteUser(int id) {

        try {
            repository.deleteById(id);
        }
        catch (Exception e){
            System.out.println("Deletion Failed");
            return "User not removed";
        }
        System.out.println("Deletion Success");
        return "User removed !! " + id;
    }


    public User registerUser(User user) {

        try{
            validateUserRegister(user);
        }
        catch (Exception e) {
            System.out.println(e);
            System.out.println("User " + user.getName() + " could not be added");
            return null;
        }

        user.setRole("User");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        System.out.println("User " + user.getName() + " added");
        return user;

    }




    public ResponseEntity<String> loginUser(User user){
          User optionalPlayer = repository.findByName(user.getName());

        try {
            validateUserLogin(user);
            Authentication authentication =  authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));

            return ResponseEntity.ok(tokenManager.generateToken(user.getName()));

        } catch (AuthenticationException e) {

            System.out.println(e);
            throw e;
        }
    }


    private void validateUserRegister(User user){

        Assert.notNull(user , "Null User");
        Assert.notNull(user.getName() , "Null User Name");
        Assert.notNull(user.getPassword() , "Null User Password");
        Assert.notNull(user.getEmail() , "Null User Email");
        Assert.isNull(getUserByName(user.getName()) , "User exists");

    }

    private void validateUserLogin(User user){
        Assert.notNull(user , "Null User");
        Assert.notNull(user.getName() , "Null User Name");
        Assert.notNull(user.getPassword() , "Null User Password");
        Assert.isTrue(user.isVerified() , "User is Not Verified");
    }


    public User updateUser(String userName , String password) {
        User existingUser = repository.findByName(userName);
        existingUser.setPassword(passwordEncoder.encode(password));
        return repository.save(existingUser);
    }
}
