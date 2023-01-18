package com.projects.quizapp.util;

import com.projects.quizapp.entity.QuestionEntity;
import com.projects.quizapp.entity.QuizResponseEntity;
import com.projects.quizapp.model.request.QuizRequest;
import com.projects.quizapp.model.request.QuizSubmitRequest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class QuizMapperUtil {

    public static QuizResponseEntity calculateAndBuildQuizResponseEntity(QuizSubmitRequest submitRequest,
                                                                         List<QuestionEntity> questionEntities,
                                                                         Long latestQuizSubmissionId) {
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
                                 .id(latestQuizSubmissionId + 1)
                                 .quizId(submitRequest.getQuizId())
                                 .userName(submitRequest.getSubmittedBy())
                                 .submittedAt(LocalDateTime.now(ZoneOffset.UTC))
                                 .score(score)
                                 .responseList(submitRequest.getQuizQuestionToResponseMap())
                                 .build();
    }

    public static QuizRequest buildNewQuizAndQuestionEntities(QuizRequest quizRequest, Long latestQuizId,
                                                              Long latestQuestionId) {
        AtomicReference<Long> latestQuestionIdAtomicReference =
                new AtomicReference<>(latestQuestionId);
        quizRequest.getQuestions().forEach(question -> {
            latestQuestionIdAtomicReference.set(latestQuestionIdAtomicReference.get() + 1);
            question.setId(latestQuestionIdAtomicReference.get());
        });
        quizRequest.setId(latestQuizId + 1);
        return quizRequest;
    }
}
