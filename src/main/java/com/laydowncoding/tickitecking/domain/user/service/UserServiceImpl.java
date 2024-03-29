package com.laydowncoding.tickitecking.domain.user.service;

import static com.laydowncoding.tickitecking.global.exception.errorcode.UserErrorCode.DUPLICATE_EMAIL;
import static com.laydowncoding.tickitecking.global.exception.errorcode.UserErrorCode.DUPLICATE_NICKNAME;
import static com.laydowncoding.tickitecking.global.exception.errorcode.UserErrorCode.DUPLICATE_USERNAME;
import static com.laydowncoding.tickitecking.global.exception.errorcode.UserErrorCode.NOT_FOUND_USER;

import com.laydowncoding.tickitecking.domain.user.dto.SignupRequestDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserUpdateRequestDto;
import com.laydowncoding.tickitecking.domain.user.entity.User;
import com.laydowncoding.tickitecking.domain.user.entity.UserRole;
import com.laydowncoding.tickitecking.domain.user.repository.UserRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();

        validateDuplicateUsername(username);
        validateDuplicateEmail(email);

        if (Objects.nonNull(nickname)) {
            validateDuplicateNickname(nickname);
        }

        User user = User.builder()
            .username(username)
            .password(password)
            .email(email)
            .nickname(requestDto.getNickname())
            .role(UserRole.USER)
            .build();
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User user = findUser(userId);
        user.update(requestDto);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new CustomRuntimeException(NOT_FOUND_USER.getMessage())
        );
    }

    private void validateDuplicateUsername(String username) {
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new CustomRuntimeException(DUPLICATE_USERNAME.getMessage());
        }
    }

    private void validateDuplicateEmail(String email) {
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new CustomRuntimeException(DUPLICATE_EMAIL.getMessage());
        }
    }

    private void validateDuplicateNickname(String nickname) {
        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new CustomRuntimeException(DUPLICATE_NICKNAME.getMessage());
        }
    }
}
