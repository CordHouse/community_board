package com.example.community_board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RefreshToken {

    @Id
    @Column(name = "keyValue")
    private String key;

    @Column(nullable = false)
    private String value;
}
