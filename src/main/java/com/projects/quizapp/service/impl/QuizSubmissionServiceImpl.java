package com.projects.quizapp.service.impl;

import com.projects.quizapp.entity.QuestionEntity;
import com.projects.quizapp.entity.QuizResponseEntity;
import com.projects.quizapp.exception.exceptions.BadRequestException;
import com.projects.quizapp.model.request.QuizSubmitRequest;
import com.projects.quizapp.model.response.QuizPlayerResponse;
import com.projects.quizapp.repository.QuestionRepository;
import com.projects.quizapp.repository.QuizResponseRepository;
import com.projects.quizapp.service.QuizSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class QuizSubmissionServiceImpl implements QuizSubmissionService {

    private final QuestionRepository questionRepository;

    private final QuizResponseRepository quizResponseRepository;

    @Autowired
    public QuizSubmissionServiceImpl(QuestionRepository questionRepository,
                                     QuizResponseRepository quizResponseRepository) {
        this.questionRepository = questionRepository;
        this.quizResponseRepository = quizResponseRepository;
    }

    @Override
    public QuizPlayerResponse submitResponse(QuizSubmitRequest submitRequest) {
        List<QuestionEntity> questionEntities = questionRepository.findQuestionsByQuizIdAndIdsIn(
                submitRequest.getQuizId(), submitRequest.getQuizQuestionToResponseMap().keySet())
                                                                  .orElseThrow(() -> new BadRequestException(
                                                                          "No quiz found with the quiz id"));
        QuizResponseEntity quizResponseEntity =
                quizResponseRepository.save(buildQuizResponseEntity(submitRequest, questionEntities));
        return QuizPlayerResponse.builder().score(quizResponseEntity.getScore()).build();
    }

    @Override
    public List<QuizResponseEntity> getQuizSubmissions(Long quizId) {
        return quizResponseRepository.findAllByQuizId(quizId)
                                     .orElseThrow(() -> new BadRequestException(
                                             "No submissions found for this quiz id : " + quizId));
    }

    private QuizResponseEntity buildQuizResponseEntity(QuizSubmitRequest submitRequest,
                                                       List<QuestionEntity> questionEntities) {
        Long id = quizResponseRepository.findResponseWithLargestId().orElse(0L) + 1;
        int score = questionEntities.stream()
                                    .filter(questionEntity -> submitRequest.getQuizQuestionToResponseMap()
                                                                           .get(questionEntity.getId())
                                                                           .containsAll(questionEntity.getAnswers()) &&
                                                              questionEntity.getAnswers()
                                                                            .containsAll(
                                                                                    submitRequest.getQuizQuestionToResponseMap()
                                                                                                 .get(questionEntity.getId())))
                                    .mapToInt(QuestionEntity::getMaxMarks)
                                    .sum();
        return QuizResponseEntity.builder()
                                 .id(id)
                                 .quizId(submitRequest.getQuizId())
                                 .userName(submitRequest.getSubmittedBy())
                                 .submittedAt(LocalDateTime.now(ZoneOffset.UTC))
                                 .score(score)
                                 .responseList(submitRequest.getQuizQuestionToResponseMap())
                                 .build();
    }
}
