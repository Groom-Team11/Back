package com.bloomgroom.domain.biggoal.application;


import com.bloomgroom.domain.biggoal.domain.BigGoal;
import com.bloomgroom.domain.biggoal.domain.Priority;
import com.bloomgroom.domain.biggoal.domain.repository.BigGoalRepository;
import com.bloomgroom.domain.biggoal.dto.request.BigGoalReq;
import com.bloomgroom.domain.biggoal.dto.request.BigGoalUpdateReq;
import com.bloomgroom.domain.smallgoal.domain.SmallGoal;
import com.bloomgroom.domain.smallgoal.domain.repository.SmallGoalRepository;
import com.bloomgroom.domain.user.domain.User;
import com.bloomgroom.domain.user.domain.repository.UserRepository;
import com.bloomgroom.global.payload.ErrorCode;
import com.bloomgroom.global.payload.exception.BusinessBaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BigGoalService {

    private final BigGoalRepository bigGoalRepository;
    private final UserRepository userRepository;
    private final SmallGoalRepository smallGoalRepository;

    //1. 장기목표 생성
    public BigGoal createBigGoal(BigGoalReq bigGoalReq, Long userId) {

        // User 객체 생성
        User user = new User();
        user.setUserId(userId);

        // 우선 순위 검증
        checkPriorityConstraints(user, bigGoalReq.getPriority());

        // BigGoal 객체 생성
        BigGoal bigGoal = new BigGoal();
        bigGoal.setUser(user);
        bigGoal.setStartDate(bigGoalReq.getStartDate());
        bigGoal.setEndDate(bigGoalReq.getEndDate());
        bigGoal.setPriority(bigGoalReq.getPriority());
        bigGoal.setContent(bigGoalReq.getContent());
        bigGoal.setGoalStatus(false); // 초기 상태는 달성되지 않음
        bigGoal.setCnt(0L); // 초기 수증기값 0으로 설정

        // 저장 및 반환
        return bigGoalRepository.save(bigGoal);
    }


    private void checkPriorityConstraints(User user, Priority priority) {

        long cnt = bigGoalRepository.countByUserAndPriority(user, priority);

        // 매우중요와 중요는 유저마다 하나씩밖에 존재할 수 없다.
        if ((priority == Priority.매우중요 && cnt >= 1) || (priority == Priority.중요 && cnt >= 1)) {
            throw new BusinessBaseException(ErrorCode.VERY_IMPORTANT_GOAL_EXISTS);
        }
    }

    //2. 장기목표 삭제
    public void deleteBigGoal(Long bigGoalId, Long userId) {
        Optional<BigGoal> bigGoalOptional = bigGoalRepository.findById(bigGoalId);

        if (!bigGoalOptional.isPresent()) {
            throw new BusinessBaseException(ErrorCode.NOT_FOUND);
        }

        BigGoal bigGoal = bigGoalOptional.get();

        //인증된 유저가 장기 목표 작성자인지 확인
        if (!bigGoal.getUser().getUserId().equals(userId)) {
            throw new BusinessBaseException(ErrorCode.UNAUTHORIZED_USER);
        }

        // 세부 목표 삭제
        List<SmallGoal> smallGoals = smallGoalRepository.findByBigGoal(bigGoal);
        smallGoalRepository.deleteAll(smallGoals);

        // 장기 목표 삭제
        bigGoalRepository.delete(bigGoal);
    }


    //3. 장기목표 수정
    public BigGoal updateBigGoal(Long bigGoalId, BigGoalUpdateReq bigGoalUpdateReq, Long userId) {

        BigGoal bigGoal = bigGoalRepository.findById(bigGoalId)
                .orElseThrow(() -> new BusinessBaseException(ErrorCode.NOT_FOUND));

        // 인증된 유저가 해당 장기목표의 소유자인지 확인
        if (!bigGoal.getUser().getUserId().equals(userId)) {
            throw new BusinessBaseException(ErrorCode.UNAUTHORIZED_USER);
        }

        if (bigGoalUpdateReq.getContent() != null) {
            bigGoal.setContent(bigGoalUpdateReq.getContent());
        }
        if (bigGoalUpdateReq.getStartDate() != null) {
            bigGoal.setStartDate(bigGoalUpdateReq.getStartDate());
        }
        if (bigGoalUpdateReq.getEndDate() != null) {
            bigGoal.setEndDate(bigGoalUpdateReq.getEndDate());
        }
        if (bigGoalUpdateReq.getPriority() != null) {
            bigGoal.setPriority(bigGoalUpdateReq.getPriority());
        }

        return bigGoalRepository.save(bigGoal);
    }


    //4.장기목표 리스트 (우선순위 순으로)
    //장기목표설정이후에서 사용하면 됨!
    public List<BigGoal> getBigGoalsSorted() {
        return bigGoalRepository.findAllByOrderByPriority();
    }

    //달성률 계산관련
    public int calculateAchievementRate(BigGoal bigGoal) {

        // 시작일과 종료일을 기준으로 일 수 계산
        long totalDays = ChronoUnit.DAYS.between(bigGoal.getStartDate().toLocalDate(), bigGoal.getEndDate().toLocalDate());


        // cnt가 0보다 큰 경우에만 달성률 계산
        if (totalDays > 0) {
            // cnt / (endDate - startDate)
            double achievementRate = ((double) bigGoal.getCnt() / totalDays) * 100;
            return (int) achievementRate;

        } else {
            return 0;
        }
    }


    //5.마감기한이 지난 것에 대해서 장기목표 goalstatus true->false로 바꿈
    public void updateGoalStatus(){

        List<BigGoal> bigGoals = bigGoalRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (BigGoal bigGoal : bigGoals) {
            if (bigGoal.getEndDate().isBefore(now) && bigGoal.getGoalStatus()) {
                bigGoal.setGoalStatus(false); // goalStatus를 false로 변경 -> 장기목표 비활성화된 것
                bigGoalRepository.save(bigGoal);
            }
        }
    }

}