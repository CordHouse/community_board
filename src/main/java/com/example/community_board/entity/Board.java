package com.example.community_board.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Column(name = "view", columnDefinition = "int default 0") // 기본값을 0으로 설정해주기 위함
    private long viewCount;

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
