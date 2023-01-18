package com.projects.quizapp.service;

import com.projects.quizapp.model.request.QuizRequest;
import com.projects.quizapp.model.response.QuizResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {
    void createQuizzes(List<QuizRequest> quizzes, String createdBy);

    QuizResponse getQuiz(Long quizId);

    QuizResponse updateQuiz(QuizRequest quiz);

    void deleteQuiz(Long quizId);

    String getShareableLinkAndUpdateQuiz(Long quizId);

    QuizResponse getQuizForPlayer(Long quizId);
}
