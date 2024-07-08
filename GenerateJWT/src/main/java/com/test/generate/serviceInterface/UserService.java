package com.test.generate.serviceInterface;

import com.test.generate.authenticationResponse.AuthenticationResponse;
import com.test.generate.model.User;

public interface UserService {
    AuthenticationResponse register(User user);
    AuthenticationResponse authentication(User request);

}
