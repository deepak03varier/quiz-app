package com.projects.quizapp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.projects.quizapp.entity.QuestionEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class QuestionResponse {

    private Long id;

    private String description;

    private int maxMarks;

    private List<String> choices;

    private List<Integer> answers;

    public static QuestionResponse from(final QuestionEntity entity, final boolean isAdmin) {
        QuestionResponseBuilder questionResponseBuilder = QuestionResponse.builder()
                                                                          .id(entity.getId())
                                                                          .description(entity.getDescription())
                                                                          .maxMarks(entity.getMaxMarks())
                                                                          .choices(entity.getChoices());
        if(isAdmin) {
            questionResponseBuilder = questionResponseBuilder.answers(entity.getAnswers());
        }
        return questionResponseBuilder.build();
    }
}
