package com.projects.quizapp.service;

import com.projects.quizapp.entity.QuizResponseEntity;
import com.projects.quizapp.model.request.QuizSubmitRequest;
import com.projects.quizapp.model.response.QuizPlayerSubmissionResponse;
import com.projects.quizapp.model.response.QuizResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizSubmissionService {

    QuizPlayerSubmissionResponse submitResponse(QuizSubmitRequest submitRequest, String submittedBy);

    List<QuizResponseEntity> getQuizSubmissions(Long quizId);

    QuizResponse getQuizForPlayer(Long quizId);
}
