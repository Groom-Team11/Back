package com.bloomgroom.domain.biggoal.application;


import com.bloomgroom.domain.biggoal.domain.BigGoal;
import com.bloomgroom.domain.biggoal.domain.Priority;
import com.bloomgroom.domain.biggoal.domain.repository.BigGoalRepository;
import com.bloomgroom.domain.biggoal.dto.request.BigGoalReq;
import com.bloomgroom.domain.biggoal.dto.request.BigGoalUpdateReq;
import com.bloomgroom.domain.user.domain.User;
import com.bloomgroom.domain.user.domain.repository.UserRepository;
import com.bloomgroom.global.payload.ErrorCode;
import com.bloomgroom.global.payload.exception.BusinessBaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BigGoalService {

    private final BigGoalRepository bigGoalRepository;
    private final UserRepository userRepository;
//    private final SubGoalRepository subGoalRepository;

    //1. 장기목표 생성
    public BigGoal createBigGoal(BigGoalReq bigGoalReq) {
        
        //사용자 검증
        Optional<User> userOptional = userRepository.findById(bigGoalReq.getUserId());
        if (!userOptional.isPresent()) {
            throw new BusinessBaseException(ErrorCode.USER_NOT_FOUND);
        }

        User user = userOptional.get();

        //우선 순위 검증
        checkPriorityConstraints(user, bigGoalReq.getPriority());
        
        BigGoal bigGoal = new BigGoal();
        bigGoal.setUser(user);
        bigGoal.setStartDate(bigGoalReq.getStartDate());
        bigGoal.setEndDate(bigGoalReq.getEndDate());
        bigGoal.setPriority(bigGoalReq.getPriority());
        bigGoal.setContent(bigGoalReq.getContent());
        bigGoal.setGoalStatus(false); // 초기 상태는 달성되지 않음

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
    //2-1) 달성된 장기 목표 삭제 -> goal_staus가 false -> true 바뀌면 삭제
    //해당 메서드는 세부 사항이 업데이트 될때 실행됨

    //달성률 계산 = 장기 목표별 달성된 세부목표 수 / 총 세부목표 수
    
    
    //2-2) 장기목표 삭제
    public void deleteBigGoal(Long bigGoalId) {

        Optional<BigGoal> bigGoalOptional = bigGoalRepository.findById(bigGoalId);
        if (!bigGoalOptional.isPresent()) {
            throw new BusinessBaseException(ErrorCode.NOT_FOUND);
        }

        BigGoal bigGoal = bigGoalOptional.get();
        bigGoalRepository.delete(bigGoal); // 장기 목표 및 해당 세부 목표들 삭제
    }

    //3. 장기목표 수정
    public BigGoal updateBigGoal(Long bigGoalId, BigGoalUpdateReq bigGoalUpdateReq) {

        Optional<BigGoal> bigGoalOptional = bigGoalRepository.findById(bigGoalId);
        if (!bigGoalOptional.isPresent()) {
            throw new BusinessBaseException(ErrorCode.NOT_FOUND);
        }

        BigGoal bigGoal = bigGoalOptional.get();

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
    public List<BigGoal> getBigGoalsSorted() {
        return bigGoalRepository.findAllByOrderByPriority();
    }

}

