package com.projects.quizapp.service.impl;

import com.projects.quizapp.entity.QuizEntity;
import com.projects.quizapp.exception.exceptions.BadRequestException;
import com.projects.quizapp.model.request.QuizRequest;
import com.projects.quizapp.model.response.QuizResponse;
import com.projects.quizapp.repository.QuizRepository;
import com.projects.quizapp.service.QuestionService;
import com.projects.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.projects.quizapp.constants.ExceptionConstants.NO_QUIZ_FOUND_ERROR_MESSAGE;
import static com.projects.quizapp.constants.ExceptionConstants.UPDATE_QUIZ_AFTER_SHARED_ERROR_MESSAGE;
import static com.projects.quizapp.constants.GenericConstants.DEFAULT_ENTITY_ID;
import static com.projects.quizapp.util.QuizMapperUtil.buildNewQuizAndQuestionEntities;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    private final QuestionService questionService;

    private final String quizAppEndpoint;

    private final String playerQuizUri;

    @Autowired
    public QuizServiceImpl(final QuizRepository quizRepository, final QuestionService questionService,
                           @Value("${quiz.app.endpoint}") final String quizAppEndpoint,
                           @Value("${quiz.app.player.quiz.uri}") final String playerQuizUri) {
        this.quizRepository = quizRepository;
        this.questionService = questionService;
        this.quizAppEndpoint = quizAppEndpoint;
        this.playerQuizUri = playerQuizUri;
    }

    @Override
    public void createQuizzes(List<QuizRequest> quizzes, String createdBy) {
        Long latestQuizId = quizRepository.findQuizWithLargestQuizId().orElse(DEFAULT_ENTITY_ID);
        Long latestQuestionId = questionService.findQuestionWithLargestQuestionId();
        quizRepository.saveAll(
                quizzes.stream()
                       .map(quizRequest -> QuizEntity.of(
                               buildNewQuizAndQuestionEntities(quizRequest, latestQuizId, latestQuestionId), createdBy))
                       .collect(Collectors.toList()));
    }

    @Override
    public QuizResponse getQuiz(Long quizId) {
        return QuizResponse.forAdmin(quizRepository.findById(quizId)
                                                   .orElseThrow(() -> new BadRequestException(
                                                           NO_QUIZ_FOUND_ERROR_MESSAGE)));
    }

    @Override
    public QuizResponse updateQuiz(QuizRequest quiz) {
        QuizEntity quizEntity = quizRepository.findById(quiz.getId())
                                              .orElseThrow(
                                                      () -> new BadRequestException(NO_QUIZ_FOUND_ERROR_MESSAGE));
        if (!quizEntity.isCanBeUpdated())
            throw new BadRequestException(UPDATE_QUIZ_AFTER_SHARED_ERROR_MESSAGE);
        quizEntity = quizRepository.save(QuizEntity.of(quiz, quizEntity.getCreatedBy()));
        return QuizResponse.forAdmin(quizEntity);
    }

    @Override
    public void deleteQuiz(Long quizId) {
        quizRepository.delete(quizRepository.findById(quizId)
                                            .orElseThrow(
                                                    () -> new BadRequestException(NO_QUIZ_FOUND_ERROR_MESSAGE)));
    }

    @Override
    public String getShareableLinkAndUpdateQuiz(Long quizId) {
        QuizEntity quizEntity = quizRepository.findById(quizId)
                                              .orElseThrow(
                                                      () -> new BadRequestException(NO_QUIZ_FOUND_ERROR_MESSAGE));
        quizEntity.setCanBeUpdated(false);
        quizRepository.save(quizEntity);
        return quizAppEndpoint + playerQuizUri + quizId;
    }

    @Override
    public QuizResponse getQuizForPlayer(Long quizId) {
        return QuizResponse.forPlayer(quizRepository.findById(quizId)
                                                    .orElseThrow(() -> new BadRequestException(
                                                            NO_QUIZ_FOUND_ERROR_MESSAGE)));
    }
}
