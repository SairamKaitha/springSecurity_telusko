package com.telusko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.telusko.entity.UserPrinciplal;
import com.telusko.entity.Users;
import com.telusko.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username); // Ensure this matches your UserRepo method
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrinciplal(user);
    }
}

