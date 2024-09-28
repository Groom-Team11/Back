package com.bloomgroom.domain.flower.domain.repository;

import com.bloomgroom.domain.flower.domain.Flower;
import com.bloomgroom.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long> {

}
