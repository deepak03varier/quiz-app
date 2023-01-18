package com.projects.quizapp.service.impl;

import com.projects.quizapp.entity.QuestionEntity;
import com.projects.quizapp.exception.exceptions.BadRequestException;
import com.projects.quizapp.repository.QuestionRepository;
import com.projects.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.projects.quizapp.constants.ExceptionConstants.NO_QUESTION_FOUND_ERROR_MESSAGE;
import static com.projects.quizapp.constants.GenericConstants.DEFAULT_ENTITY_ID;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(final QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<QuestionEntity> findQuestionsByQuizIdAndQuestionIds(final Long quizId, final Set<Long> keySet) {
        return questionRepository.findQuestionsByQuizIdAndIdsIn(quizId, keySet)
                                 .orElseThrow(() -> new BadRequestException(NO_QUESTION_FOUND_ERROR_MESSAGE));
    }

    @Override
    public Long findQuestionWithLargestQuestionId() {
        return questionRepository.findQuestionWithLargestQuestionId().orElse(DEFAULT_ENTITY_ID);
    }
}
