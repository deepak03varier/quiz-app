package com.projects.quizapp.service.impl;

import com.projects.quizapp.entity.QuestionEntity;
import com.projects.quizapp.entity.QuizResponseEntity;
import com.projects.quizapp.model.request.QuizSubmitRequest;
import com.projects.quizapp.repository.QuizResponseRepository;
import com.projects.quizapp.service.QuestionService;
import com.projects.quizapp.util.QuizMapperUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.projects.quizapp.constants.TestConstants.DEFAULT_ID;
import static com.projects.quizapp.constants.TestConstants.DEFAULT_QUESTION_ID;
import static com.projects.quizapp.constants.TestConstants.DEFAULT_QUIZ_ID;
import static com.projects.quizapp.constants.TestConstants.TEST_USER;

public class QuizSubmissionServiceImplTest {

    @InjectMocks
    QuizSubmissionServiceImpl quizSubmissionService;

    @Mock
    QuestionService questionService;

    @Mock
    QuizResponseRepository quizResponseRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQuizSubmissionReturnsSuccess() {
        /**
         * Given Data
         */
        Map<Long, List<Integer>> map = new HashMap<>();
        map.put(DEFAULT_QUESTION_ID, Collections.singletonList(1));
        QuizSubmitRequest request = new QuizSubmitRequest();
        request.setQuizId(DEFAULT_QUIZ_ID);
        request.setQuizQuestionToResponseMap(map);
        List<QuestionEntity> questionEntities = Collections.singletonList(
                QuestionEntity.builder().id(request.getQuizId()).answers(Collections.singletonList(1)).build());
        QuizResponseEntity quizResponseEntity =
                QuizMapperUtil.calculateAndBuildQuizResponseEntity(request, questionEntities, DEFAULT_ID);

        /**
         * When
         */
        Mockito.when(questionService.findQuestionsByQuizIdAndQuestionIds(DEFAULT_QUIZ_ID, map.keySet()))
               .thenReturn(questionEntities);
        Mockito.when(quizResponseRepository.save(Mockito.any())).thenReturn(quizResponseEntity);

        Assert.assertEquals(quizSubmissionService.submitResponse(request, TEST_USER).getScore(),
                            quizResponseEntity.getScore());
    }
}