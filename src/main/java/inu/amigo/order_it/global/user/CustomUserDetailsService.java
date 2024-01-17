package inu.amigo.order_it.global.user;

import inu.amigo.order_it.global.config.user.CustomUserDetails;
import inu.amigo.order_it.user.UserRepository;
import inu.amigo.order_it.user.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("[loadUserByUsername] username : {}", username);

        if (!userRepository.existsByUsername(username)) {
            log.error("[loadUserByUsername] Username is not found,username : {}", username);
            throw new UsernameNotFoundException("This Username is not exist.");
        }
        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity != null) return new CustomUserDetails(userEntity) {
        };
        return null;
    }
}
