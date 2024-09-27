package com.bloomgroom.domain.smallgoal.domain.repository;

import com.bloomgroom.domain.biggoal.domain.BigGoal;
import com.bloomgroom.domain.smallgoal.domain.SmallGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SmallGoalRepository extends JpaRepository<SmallGoal, Long> {
    List<SmallGoal> findByBigGoal(BigGoal bigGoal);
    Optional<SmallGoal> findBySmallGoalId(Long smallGoalId);
}
