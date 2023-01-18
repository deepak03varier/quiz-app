package com.projects.quizapp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.projects.quizapp.entity.QuizEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class QuizResponse {

    private Long id;

    private String name;

    private String createdBy;

    private Boolean canBeUpdated;

    private List<QuestionResponse> questions;

    public static QuizResponse forPlayer(final QuizEntity entity) {
        return QuizResponse.builder()
                           .id(entity.getId())
                           .name(entity.getName())
                           .createdBy(entity.getCreatedBy())
                           .questions(entity.getQuestions()
                                            .stream()
                                            .map(questionEntity -> QuestionResponse.from(questionEntity, false))
                                            .collect(Collectors.toList()))
                           .build();
    }

    public static QuizResponse forAdmin(final QuizEntity entity) {
        return forPlayer(entity).toBuilder()
                           .questions(entity.getQuestions()
                                            .stream()
                                            .map(questionEntity -> QuestionResponse.from(questionEntity, true))
                                            .collect(Collectors.toList()))
                           .canBeUpdated(entity.isCanBeUpdated())
                           .build();
    }
}
