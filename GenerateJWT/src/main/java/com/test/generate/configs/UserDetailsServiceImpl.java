package com.test.generate.configs;

import com.test.generate.dao.UserRepository;
import com.test.generate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = repository.findByUsername(username);
        if(userOpt.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        return new UserDetailsImpl(userOpt.get());
    }
}
