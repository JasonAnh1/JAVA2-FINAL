package com.samsung.bookmanagerment.service;


import com.samsung.bookmanagerment.configuration.Translator;
import com.samsung.bookmanagerment.entity.Role;
import com.samsung.bookmanagerment.entity.User;
import com.samsung.bookmanagerment.entity.contants.RoleName;
import com.samsung.bookmanagerment.repository.user.UserRepository;
import com.samsung.bookmanagerment.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class BaseService {

    @Autowired
    protected UserRepository userRepository;

    public final Logger logger = LoggerFactory.getLogger(getClass());

    public User getUser() {
        User user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            user = userRepository.findUserById(userPrincipal.getId());
        }

        return user;
    }
    protected User getUser(RoleName... roles) throws Exception {
        User user = getUser();
        for (Role userRole: user.getRoles()) {
            if(userRole.getName() == RoleName.ROLE_ADMIN) {
                return user;
            }
            for(RoleName role : roles) {
                if(userRole.getName() == role) {
                    return user;
                }
            }
        }
        throw new Exception(Translator.toLocale("invalid_permission"));
    }
}
