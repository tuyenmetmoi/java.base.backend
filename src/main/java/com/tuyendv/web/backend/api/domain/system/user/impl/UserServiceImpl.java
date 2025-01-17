package com.tuyendv.web.backend.api.domain.system.user.impl;

import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.common.Constants;
import com.tuyendv.web.backend.api.config.security.handler.CustomException;
import com.tuyendv.web.backend.api.domain.system.user.service.UserService;
import com.tuyendv.web.backend.api.dto.system.user.UserRequestDTO;
import com.tuyendv.web.backend.api.dto.system.user.UserResponseDTO;
import com.tuyendv.web.backend.api.entity.system.loginJwt.JwtUser;
import com.tuyendv.web.backend.api.repository.system.loginJwt.JwtUserRepository;
import com.tuyendv.web.backend.api.repository.system.role.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUserRepository userRepository;

    @Override
    public UserResponseDTO registerUser(UserRequestDTO dto) {
        Optional<JwtUser> checkUser = userRepository.findByUserNameAndUseYn(dto.getUserName(), Constants.STATE_Y);
        Optional<JwtUser> checkEmail = userRepository.findByEmailAndUseYn(dto.getEmail(), Constants.STATE_Y);

        //check exist by username or email
        if (checkUser.isPresent() || checkEmail.isPresent()) {
            throw new CustomException(ApiStatus.USER_EXISTED);
        }

        //check regex email
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        if (! pattern.matcher(dto.getEmail()).matches()) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        String passwordRandom = generateRandomString(10); //send for user by email or otherwise
        JwtUser entity = modelMapper.map(dto, JwtUser.class);
        entity.setPassword(passwordEncoder.encode(passwordRandom));//encode password
        entity.setUseYn(Constants.STATE_Y);

        userRepository.save(entity);

        return modelMapper.map(entity, UserResponseDTO.class);
    }

    private String generateRandomString(int length) {
        if (length < 2) {
            throw new IllegalArgumentException(
                    "Length must be at least 2 to include both uppercase and lowercase characters.");
        }

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // Add one random uppercase character
        sb.append((char) (random.nextInt(26) + 'A'));

        // Add one random lowercase character
        sb.append((char) (random.nextInt(26) + 'a'));

        // Add remaining characters
        for (int i = 2; i < length; i++) {
            int rand = random.nextInt(3);
            if (rand == 0) {
                sb.append((char) (random.nextInt(26) + 'A')); // Uppercase
            } else if (rand == 1) {
                sb.append((char) (random.nextInt(26) + 'a')); // Lowercase
            } else {
                sb.append((char) (random.nextInt(10) + '0')); // Digit
            }
        }

        // Shuffle the characters to make the string more random
        char[] charArray = sb.toString().toCharArray();
        for (int i = charArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = charArray[ index ];
            charArray[ index ] = charArray[ i ];
            charArray[ i ] = temp;
        }

        return new String(charArray);
    }

}
