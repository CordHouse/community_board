package com.example.community_board.jwt;

import com.example.community_board.dto.user.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component // 토큰 유효성 검사, Authentication을 통하여 토큰 인증, 토큰을 통하여 인증 객체 생성
@Slf4j // 콘솔 창에 로그를 띄워주기 위하여 사용
public class TokenProvider implements InitializingBean {

    private static final String AUTHORIZATION_KEY = "auth";

    private String secret; // application.yml에 선언된 secret값을 가져와서 저장하기 위함

    private Long tokenValidationTime; // application.yml에 선언된 tokenValidationTime을 가져와서 저장하기 위함

    private Long refreshTokenValidationTime;

    private Key key; // 비밀키 암호화에서 활용할 키를 저장함

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.tokenValidationTime}") Long validationTime) {
        this.secret = secret;
        this.tokenValidationTime = validationTime * 1000; // 1800초(30분)
        this.refreshTokenValidationTime = validationTime * 2 * 1000; // 3600초(1시간)
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] secret_key = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(secret_key);
    }

    public TokenDto createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        Date expire = new Date(now + tokenValidationTime); // 토큰의 유효 기간 설정

        Date refreshExpire = new Date(now + refreshTokenValidationTime);

        String originToken = Jwts.builder()
                            .setExpiration(expire)
                .setSubject(authentication.getName())
                .claim(AUTHORIZATION_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(refreshExpire)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.builder()
                .originToken(originToken)
                .validationTime(expire.getTime())
                .refreshToken(refreshToken)
                .grantedType("Bearer ")
                .build();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token).getBody();

        if(claims.get(AUTHORIZATION_KEY) == null) {
            throw new RuntimeException("해당 클레임에 정보가 존재하지 않습니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORIZATION_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        catch(SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        }
        catch(ExpiredJwtException e) {
            log.info("기간이 만료된 JWT 토큰입니다.");
        }
        catch(UnsupportedJwtException e) {
            log.info("지원하지 않는 형식의 JWT 토큰입니다.");
        }
        catch(IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }

        return false;
    }
}
