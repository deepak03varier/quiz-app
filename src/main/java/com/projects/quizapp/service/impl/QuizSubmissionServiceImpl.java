package com.projects.quizapp.service.impl;

import com.projects.quizapp.constants.ExceptionConstants;
import com.projects.quizapp.entity.QuestionEntity;
import com.projects.quizapp.entity.QuizResponseEntity;
import com.projects.quizapp.exception.exceptions.BadRequestException;
import com.projects.quizapp.model.request.QuizSubmitRequest;
import com.projects.quizapp.model.response.QuizPlayerSubmissionResponse;
import com.projects.quizapp.model.response.QuizResponse;
import com.projects.quizapp.repository.QuizResponseRepository;
import com.projects.quizapp.service.QuestionService;
import com.projects.quizapp.service.QuizService;
import com.projects.quizapp.service.QuizSubmissionService;
import com.projects.quizapp.util.QuizMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.projects.quizapp.constants.GenericConstants.DEFAULT_ENTITY_ID;

@Service
public class QuizSubmissionServiceImpl implements QuizSubmissionService {

    private final QuizResponseRepository quizResponseRepository;

    private final QuestionService questionService;

    private final QuizService quizService;

    @Autowired
    public QuizSubmissionServiceImpl(final QuizResponseRepository quizResponseRepository,
                                     final QuestionService questionService,
                                     final QuizService quizService) {
        this.questionService = questionService;
        this.quizResponseRepository = quizResponseRepository;
        this.quizService = quizService;
    }

    @Override
    public QuizPlayerSubmissionResponse submitResponse(QuizSubmitRequest submitRequest, String submittedBy) {
        submitRequest.setSubmittedBy(submittedBy);
        List<QuestionEntity> questionEntities =
                questionService.findQuestionsByQuizIdAndQuestionIds(submitRequest.getQuizId(),
                                                                    submitRequest.getQuizQuestionToResponseMap()
                                                                                 .keySet());
        Long latestQuizSubmissionId = quizResponseRepository.findResponseWithLargestId().orElse(DEFAULT_ENTITY_ID);
        QuizResponseEntity quizResponseEntity =
                quizResponseRepository.save(
                        QuizMapperUtil.calculateAndBuildQuizResponseEntity(submitRequest, questionEntities,
                                                                           latestQuizSubmissionId));
        return QuizPlayerSubmissionResponse.builder().score(quizResponseEntity.getScore()).build();
    }

    @Override
    public List<QuizResponseEntity> getQuizSubmissions(Long quizId) {
        return quizResponseRepository.findAllByQuizId(quizId)
                                     .orElseThrow(() -> new BadRequestException(
                                             ExceptionConstants.NO_SUBMISSIONS_FOUND_ERROR_MESSAGE + quizId));
    }

    @Override
    public QuizResponse getQuizForPlayer(Long quizId) {
        return quizService.getQuizForPlayer(quizId);
    }
}
