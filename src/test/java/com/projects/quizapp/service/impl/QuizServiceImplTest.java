package com.projects.quizapp.service.impl;

import com.projects.quizapp.entity.QuizEntity;
import com.projects.quizapp.exception.exceptions.BadRequestException;
import com.projects.quizapp.model.request.QuizRequest;
import com.projects.quizapp.repository.QuizRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static com.projects.quizapp.constants.TestConstants.DEFAULT_QUIZ_ID;

public class QuizServiceImplTest {

    @InjectMocks
    QuizServiceImpl quizService;

    @Mock
    QuizRepository quizRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateQuizReturnsSuccess() {
        /**
         * Given Data
         */
        QuizEntity quizEntity = QuizEntity.builder()
                                          .questions(new ArrayList<>())
                                          .canBeUpdated(true)
                                          .id(DEFAULT_QUIZ_ID)
                                          .build();

        QuizRequest quizRequest = new QuizRequest();
        quizRequest.setQuestions(new ArrayList<>());

        /**
         * When
         */
        Mockito.when(quizRepository.findById(Mockito.any())).thenReturn(Optional.of(quizEntity));
        Mockito.when(quizRepository.save(Mockito.any())).thenReturn(quizEntity);

        Assert.assertEquals(quizService.updateQuiz(quizRequest).getCanBeUpdated(), quizEntity.isCanBeUpdated());
    }

    @Test(expected = BadRequestException.class)
    public void testUpdateQuizThrowsBadRequestExceptionWhenQuizIsShared() {
        /**
         * When
         */
        Mockito.when(quizRepository.findById(Mockito.any()))
               .thenReturn(Optional.of(QuizEntity.builder().canBeUpdated(false).build()));

        quizService.updateQuiz(new QuizRequest());
    }
}