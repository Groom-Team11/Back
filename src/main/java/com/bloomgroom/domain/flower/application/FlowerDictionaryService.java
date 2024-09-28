package com.bloomgroom.domain.flower.application;

import com.bloomgroom.domain.flower.domain.Flower;
import com.bloomgroom.domain.flower.domain.FlowerDictionary;
import com.bloomgroom.domain.flower.domain.repository.FlowerDictionaryRepository;
import com.bloomgroom.domain.flower.domain.repository.FlowerRepository;
import com.bloomgroom.domain.flower.dto.response.FlowerDetailRes;
import com.bloomgroom.domain.flower.dto.response.FlowerListRes;
import com.bloomgroom.domain.flower.dto.response.NewFlowerRes;
import com.bloomgroom.domain.user.domain.User;
import com.bloomgroom.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlowerDictionaryService {

    private final FlowerRepository flowerRepository;
    private final FlowerDictionaryRepository flowerDictionaryRepository;

    public ResponseEntity<?> getFlowerList(User user) {
        List<FlowerDictionary> flowers = flowerDictionaryRepository.findAllByUser(user);
        List<FlowerDetailRes> flowerDetailResList = flowers.stream().map(flower -> FlowerDetailRes.builder()
                .flowerId(flower.getFlower().getFlowerId())
                .flowerName(flower.getFlower().getFlowerName())
                .flowerImage(flower.getFlower().getFlowerImage())
                .flowerMean(flower.getFlower().getFlowerMean())
                .isAcquired(flower.getIsAcquired())
                .build()).toList();

        FlowerListRes flowerListRes = FlowerListRes.builder()
                .flowerList(flowerDetailResList)
                .build();

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(flowerListRes)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<?> getRandomFlower(User user) {

        // 사용자의 꽃 도감 조회
        List<FlowerDictionary> flowers = flowerDictionaryRepository.findAllByUser(user);

        // FlowerDictionary 중 랜덤으로 하나를 선택 (중복 허용)
        FlowerDictionary randomFlower = flowers.get((int) (Math.random() * flowers.size()));

        // 새로 획득한 꽃이라면 isAcquired를 true로 변경 (등록 로직)
        if (!randomFlower.getIsAcquired()) {
            randomFlower.setIsAcquired(true);
            flowerDictionaryRepository.save(randomFlower);
        }

        // NewFlowerRes 객체 생성
        NewFlowerRes newFlowerRes = NewFlowerRes.builder()
                .flowerImage(randomFlower.getFlower().getFlowerImage())
                .build();

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(newFlowerRes)
                .build();

        return ResponseEntity.ok(apiResponse);

    }

    @Transactional
    public void createDictionary(User user) {
        List<Flower> flowers = flowerRepository.findAll();
        List<FlowerDictionary> flowerDictionaries = new ArrayList<>();
        flowers.forEach(flower -> {
            FlowerDictionary flowerDictionary = FlowerDictionary.builder()
                    .user(user)
                    .flower(flower)
                    .isAcquired(false)
                    .build();
            flowerDictionaries.add(flowerDictionary);
        });
        flowerDictionaryRepository.saveAll(flowerDictionaries);
    }
}
