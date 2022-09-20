package com.example.community_board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username; // 사용자 이름

    @Column(nullable = false, unique = true)
    private String userId; // 사용자 ID

    @Column(nullable = false)
    private String password; // 사용자 비밀 번호

    @Column(nullable = false, unique = true)
    private String email; // 사용자 Email

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority; // 사용자 권한
}
