package com.bloomgroom.domain.biggoal.domain.repository;

import com.bloomgroom.domain.biggoal.domain.BigGoal;
import com.bloomgroom.domain.biggoal.domain.Priority;
import com.bloomgroom.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BigGoalRepository extends JpaRepository<BigGoal, Long> {
    long countByUserAndPriority(User user, Priority priority);

    @Query("SELECT b FROM BigGoal b ORDER BY " +
            "CASE b.priority WHEN com.bloomgroom.domain.biggoal.domain.Priority.매우중요 THEN 1 " +
            "WHEN com.bloomgroom.domain.biggoal.domain.Priority.중요 THEN 2 " +
            "WHEN com.bloomgroom.domain.biggoal.domain.Priority.보통 THEN 3 END, " +
            "CASE WHEN b.priority = com.bloomgroom.domain.biggoal.domain.Priority.보통 THEN b.endDate END ASC")
    List<BigGoal> findAllByOrderByPriority();
}

