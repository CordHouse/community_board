package com.example.community_board.service;

import com.example.community_board.dto.user.*;
import com.example.community_board.entity.Authority;
import com.example.community_board.entity.RefreshToken;
import com.example.community_board.entity.User;
import com.example.community_board.exception.user.NotValidTokenException;
import com.example.community_board.exception.user.UserIdCollisionException;
import com.example.community_board.exception.user.UserNotFoundException;
import com.example.community_board.exception.user.UserWrongPasswordException;
import com.example.community_board.jwt.TokenProvider;
import com.example.community_board.repository.RefreshTokenRepository;
import com.example.community_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final TokenProvider tokenProvider;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional // 회원 가입 로직
    public void signUp(UserSignUpRequestDto requestDto) {
        Optional<User> findUser = userRepository.findUserByUserId(requestDto.getUserId()); // 사용자 id를 통하여 검색

        if(findUser.isPresent()) {
            throw new UserIdCollisionException(); // 해당 유저가 존재한다는 뜻이므로, 오류 반환
        }

        User user; // 저장을 위한 User 객체 하나 생성

        if(requestDto.getUserId().equals("admin")) {
            user = User.builder() // 작성자의 ID값이 admin인 경우, 관리자 권한 획득
                    .username(requestDto.getUsername())
                    .userId(requestDto.getUserId())
                    .password(bCryptPasswordEncoder.encode(requestDto.getPassword().toLowerCase()))
                    .email(requestDto.getEmail())
                    .authority(Authority.ROLE_ADMIN)
                    .build();

            userRepository.save(user); // 관리자 권한을 가진 User Entity 저장
        }
        else {
            user = User.builder() // 그 외의 경우에는 일반 권한을 가진 유저로 회원가입을 진행하게됨
                    .username(requestDto.getUsername())
                    .userId(requestDto.getUserId())
                    .password(bCryptPasswordEncoder.encode(requestDto.getPassword().toLowerCase()))
                    .email(requestDto.getEmail())
                    .authority(Authority.ROLE_USER)
                    .build();

            userRepository.save(user); // 일반 권한을 가진 유저 객체 저장
        }
    }

    @Transactional // 로그인 로직
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        User findUser = userRepository.findUserByUserId(requestDto.getUserId())
                .orElseThrow(UserNotFoundException::new); // UserId를 통하여 User 검색

        if(bCryptPasswordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {

            UsernamePasswordAuthenticationToken authenticationToken =
                    requestDto.getAuthenticationToken();

            Authentication authentication = authenticationManagerBuilder
                    .getObject().authenticate(authenticationToken);

            TokenDto jwt = tokenProvider.createToken(authentication);

            RefreshToken refreshToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(jwt.getRefreshToken())
                    .build();

            refreshTokenRepository.save(refreshToken);

            return new UserLoginResponseDto(jwt.getOriginToken(), jwt.getRefreshToken());
        }
        throw new UserWrongPasswordException();
    }

    @Transactional// 토큰 재발급 로직
    public TokenReissueDto reIssue(TokenReissueDto requestDto) {
        if(!tokenProvider.validateToken(requestDto.getRefreshToken())) {
            throw new NotValidTokenException();
        }

        Authentication authentication = tokenProvider.getAuthentication(requestDto.getOriginToken());

        RefreshToken refreshToken = refreshTokenRepository
                .findById(authentication.getName())
                .orElseThrow(() -> new RuntimeException("현재 로그인 하지 않은 회원입니다."));

        if(!refreshToken.getValue().equals(requestDto.getRefreshToken())) {
            throw new RuntimeException("토큰 정보가 일치하지 않습니다.");
        }

        TokenDto tokenDto = tokenProvider.createToken(authentication);

        RefreshToken refreshTokenValue = RefreshToken.builder()
                .key(tokenDto.getOriginToken())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshTokenValue);

        return new TokenReissueDto(tokenDto.getOriginToken(), tokenDto.getRefreshToken());
    }

    @Transactional //email의 unique라는 속성을 활용하여 User Entity를 검색함
    public String findUserId(UserEmailRequestDto requestDto) {

        User findUser = userRepository.findUserByEmail(requestDto.getEmail()).orElseThrow(UserNotFoundException::new);

        return findUser.getUserId(); // 검색한 User Entity의 Userid만을 리턴해줌
    }

    @Transactional // 아이디 검색 로직과 마찬가지로, email의 unique라는 속성을 활용하여 User Entity를 검색함
    public String findUserPassword(UserEmailRequestDto requestDto) {

        User findUser = userRepository.findUserByEmail(requestDto.getEmail()).orElseThrow(UserNotFoundException::new);

        LocalTime tmp = LocalTime.now();

        String reGeneratedPassword = tmp.toString().replaceAll("[^0-9]", "");

        findUser.setPassword(bCryptPasswordEncoder.encode(reGeneratedPassword));

        return reGeneratedPassword;
    }

    @Transactional // UserPasswordEditRequestDto를 통하여 사용자의 현재 비밀번호와 바꿀 비밀번호를 입력받음.
    public void editPassword(UserPasswordEditRequestDto requestDto) {
        String nowLoginUserId = SecurityContextHolder.getContext()
                .getAuthentication().getName(); // 현재 사용자의 ID값을 받음

        User nowLoginUser = userRepository.findUserByUserId(nowLoginUserId)
                .orElseThrow(UserNotFoundException::new); // 사용자의 ID값은 unique 속성이 부여되어 있으므로, UserID를 통하여 사용자 검색

        log.info(nowLoginUser.getAuthority().toString());

        if(bCryptPasswordEncoder.matches(requestDto.getOriginPassword(), nowLoginUser.getPassword())) {
            nowLoginUser
                    .setPassword(bCryptPasswordEncoder.encode(requestDto.getRefreshPassword().toLowerCase())); // 검색한 사용자의 비밀번호와 입력받은 비밀번호를 비교
        }

        else throw new UserWrongPasswordException();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        for(GrantedAuthority s : authentication.getAuthorities()) {
            log.info(s.getAuthority());
        }
    }

    @Transactional
    public void delegateUser(UserDelegateRequestDto requestDto) {
        User findUser = userRepository.findUserByUserId(requestDto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        findUser.setAuthority(Authority.ROLE_MANAGER);
    }
}
