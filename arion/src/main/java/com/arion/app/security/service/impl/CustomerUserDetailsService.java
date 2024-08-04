package com.arion.app.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arion.app.security.mapper.UserMapper;
import com.arion.app.security.service.LoginUserVO;
import com.arion.app.security.service.UserVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Attempting to load user by username: {}", username);
        
        String[] parts = username.split(":", 4);
        if (parts.length != 4) {
            log.error("Username format is incorrect");
            throw new UsernameNotFoundException("Username format is incorrect");
        }

        String loginType = parts[0];
        String companyCode = parts[1];
        String userId = parts[2];
        String password = parts[3];

        log.debug("Login type: {}, Company code: {}, User ID: {}", loginType, companyCode, userId);

        UserVO userVO;

        if ("company".equals(loginType)) {
            userVO = userMapper.getCompanyUserInfo(companyCode, userId);
        } else if ("employee".equals(loginType)) {
            userVO = userMapper.getEmployeeUserInfo(companyCode, userId);
        } else {
            log.error("Unknown login type");
            throw new UsernameNotFoundException("Unknown login type");
        }

        if (userVO == null) {
            log.error("User not found for login type: {}, Company code: {}, User ID: {}", loginType, companyCode, userId);
            throw new UsernameNotFoundException("User not found");
        }

        log.debug("User found: {}", userVO);

        if (!passwordEncoder.matches(password, userVO.getLoginPw())) {
            log.error("Password does not match for user: {}", userVO.getLoginId());
            throw new UsernameNotFoundException("Password does not match");
        }

        return new LoginUserVO(userVO);
    }
}
