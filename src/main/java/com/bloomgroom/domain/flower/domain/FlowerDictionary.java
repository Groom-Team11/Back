package com.bloomgroom.domain.flower.domain;

import com.bloomgroom.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "flower_dictionary_entity")
public class FlowerDictionary {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long flowerDictionaryId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flower_id")
    private Flower flower;

    private Boolean isAcquired;

    @Builder
    public FlowerDictionary(User user, Flower flower, Boolean isAcquired) {
        this.user = user;
        this.flower = flower;
        this.isAcquired = isAcquired;
    }
}
