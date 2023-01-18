package com.projects.quizapp.service;

import com.projects.quizapp.entity.QuestionEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface QuestionService {
    List<QuestionEntity> findQuestionsByQuizIdAndQuestionIds(Long quizId, Set<Long> keySet);

    Long findQuestionWithLargestQuestionId();
}
