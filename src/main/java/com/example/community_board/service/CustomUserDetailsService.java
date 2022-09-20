package com.example.community_board.service;

import com.example.community_board.entity.User;
import com.example.community_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional // 사용자의 아이디를 통하여 사용자를 검색
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findUserByUserId(username)
                .map(this::createUserDetails)
                .orElseThrow(()->new UsernameNotFoundException(username + "이 존재하지 않습니다."));
    }

    public UserDetails createUserDetails(User account) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(account.getAuthority().toString());

        return new org.springframework.security.core.userdetails.User
                (account.getUserId(), account.getPassword(), Collections.singleton(grantedAuthority));
    }
}
