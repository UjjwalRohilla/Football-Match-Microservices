package com.test.generate.serviceImplementation;

import com.test.generate.authenticationResponse.AuthenticationResponse;
import com.test.generate.dao.UserRepository;
import com.test.generate.jwtService.JWTService;
import com.test.generate.model.User;
import com.test.generate.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public AuthenticationResponse register(User request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = this.userRepository.save(request);
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
    @Override
    public AuthenticationResponse authentication(User request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername()
                ,request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}
