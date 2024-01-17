package inu.amigo.order_it.user;

import inu.amigo.order_it.user.entity.Role;
import inu.amigo.order_it.user.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JoinService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDto joinDto) {
        log.info("[joinProcess] username = {}", joinDto.getUsername());

        if (userRepository.existsByUsername(joinDto.getUsername())) {
            log.error("[joinProcess] Same username is existed, username = {}", joinDto.getUsername());
            throw new RuntimeException("Same username is existed");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(joinDto.getUsername())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword())) // BCryptPasswordEncoder 적용
                .role(Role.USER)
                .build();

        userRepository.save(userEntity);
    }
}
