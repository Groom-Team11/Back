package com.bloomgroom.domain.flower.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "flower_entity")
public class Flower {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long flowerId;

    @Column(nullable = false)
    private String flowerName;

    @Column(nullable = false)
    private String flowerImage;

    @Column(nullable = false)
    private String flowerMean;
}
