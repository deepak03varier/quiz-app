package com.projects.quizapp.service;

import com.projects.quizapp.entity.QuizResponseEntity;
import com.projects.quizapp.model.request.QuizSubmitRequest;
import com.projects.quizapp.model.response.QuizPlayerResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizSubmissionService {

    QuizPlayerResponse submitResponse(QuizSubmitRequest submitRequest);

    List<QuizResponseEntity> getQuizSubmissions(Long quizId);
}
