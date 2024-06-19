package com.eaglevision.Backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.User;
import com.eaglevision.Backend.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName).get();
    }

    public Boolean userExistsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

}
