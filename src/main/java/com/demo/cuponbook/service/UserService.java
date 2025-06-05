package com.demo.cuponbook.service;

import lombok.RequiredArgsConstructor;
import com.demo.cuponbook.dto.LoginDTO;
import com.demo.cuponbook.dto.UserDTO;
import com.demo.cuponbook.entity.UserEntity;
import com.demo.cuponbook.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

}
