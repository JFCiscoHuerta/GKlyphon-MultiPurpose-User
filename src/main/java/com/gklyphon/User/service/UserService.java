/**
 * MIT License
 *
 * Copyright (c) 2024 JFCiscoHuerta
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gklyphon.User.service;

import com.gklyphon.User.exception.custom.UserNotFoundException;
import com.gklyphon.User.model.dto.UserDTO;
import com.gklyphon.User.model.dto.UserRequestDTO;
import com.gklyphon.User.model.entity.User;
import com.gklyphon.User.model.mapper.IUserMapper;
import com.gklyphon.User.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class UserService {

    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

    public UserService(IUserMapper userMapper, IUserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return userMapper.toUserDTO(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id)));
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userMapper.toUsersDTO(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Transactional
    public UserDTO save(UserRequestDTO userRequestDTO) {
        User user = userMapper.toUserFromUserRequestDTO(userRequestDTO);
        initializeUserDefaults(user);
        return userMapper.toUserDTO(userRepository.save(user));
    }

    private static void initializeUserDefaults(User user) {
        user.setStatus(true);
        user.setCreateAt(LocalDate.now());
        updateTimestamps(user);
    }

    private static void updateTimestamps(User user) {
        user.setUpdateAt(LocalDate.now());
        user.setStatusChangeAt(LocalDate.now());
    }

    @Transactional
    public UserDTO disableUser(Long id) {
        return changeUserStatus(id, false);
    }

    @Transactional
    public UserDTO enableUser(Long id) {
        return changeUserStatus(id, true);
    }

    private UserDTO changeUserStatus(Long id, Boolean status) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setStatus(status);
        user.setStatusChangeAt(LocalDate.now());
        updateTimestamps(user);
        log.info("Status");
        return userMapper.toUserDTO(userRepository.save(user));
    }
}
