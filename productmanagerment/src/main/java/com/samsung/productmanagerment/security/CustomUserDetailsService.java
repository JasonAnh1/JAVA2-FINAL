package com.samsung.bookmanagerment.security;



import com.samsung.bookmanagerment.configuration.Translator;
import com.samsung.bookmanagerment.entity.User;
import com.samsung.bookmanagerment.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String phone) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(Translator.toLocale("user_not_found_with_phone"), phone)));

        return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticationFilter
    public UserDetails loadUserById(final long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

        return UserPrincipal.create(user);
    }
}
