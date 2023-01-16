package com.projects.quizapp.service;

import com.projects.quizapp.entity.QuizEntity;
import com.projects.quizapp.model.request.QuizRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {
    void createQuizzes(List<QuizRequest> quizzes);

    QuizEntity getQuiz(Long quizId);

    QuizEntity updateQuiz(QuizRequest quiz);

    void deleteQuiz(Long quizId);
}
