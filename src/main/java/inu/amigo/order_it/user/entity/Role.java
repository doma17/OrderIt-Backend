package inu.amigo.order_it.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");
    // 새로운 Role 추가시 key에 "ROLE_*" 형식으로 생성

    private final String key;
}
