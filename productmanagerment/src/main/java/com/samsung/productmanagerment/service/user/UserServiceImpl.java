package com.samsung.bookmanagerment.service.user;


import com.samsung.bookmanagerment.configuration.Translator;
import com.samsung.bookmanagerment.entity.Role;
import com.samsung.bookmanagerment.entity.User;
import com.samsung.bookmanagerment.entity.contants.RoleName;
import com.samsung.bookmanagerment.repository.user.RoleRepository;
import com.samsung.bookmanagerment.repository.user.UserRepository;
import com.samsung.bookmanagerment.security.CustomUserDetailsService;
import com.samsung.bookmanagerment.security.JwtTokenProvider;
import com.samsung.bookmanagerment.security.UserPrincipal;
import com.samsung.bookmanagerment.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
class UserServiceImpl extends BaseService implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Autowired
    private UserRepository userRepository;



    /////////////////////User///////////




    public User signin(User request) throws Exception {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword()));
        } catch (Exception e) {
            throw new Exception(Translator.toLocale("login_fail"));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByPhone(request.getPhone()).orElseThrow(() -> new UsernameNotFoundException(String.format(Translator.toLocale("user_not_found_with_phone"), request.getPhone())));
        if (user.getDeleted()) {
            throw new Exception("Tài khoản đã bị xoá");
        }
        String jwt = tokenProvider.generateToken(authentication);


        user.setAccessToken(jwt);
        return user;
    }

    @Override
    public User signup(User request) throws Exception {
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new Exception(Translator.toLocale("phone_exists"));
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new Exception(Translator.toLocale("email_exists"));
        }
        if (userRepository.existsByName(request.getName())) {
            throw new Exception(Translator.toLocale("name_exists"));
        }


        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("User Role not set."));
        request.setRoles(Collections.singleton(userRole));
        User result = userRepository.save(request);
        result.setStatus(true);
//        sendNotification(result);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(result.getPhone());
        String jwt = tokenProvider.generateTokenByUser((UserPrincipal) userDetails);
        result.setAccessToken(jwt);
        return result;
    }

    @Override
    public User adminAddUser(User request) throws Exception {
        return null;
    }

    @Override
    public User adminSignin(User request) throws Exception {
        return null;
    }


}
