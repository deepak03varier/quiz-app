package com.projects.quizapp.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class QuizRequest {

    private Long id;

    @NotNull(message = "Quiz name required.")
    private String name;

    @NotNull(message = "Questions required.")
    private List<QuizQuestionRequest> questions;
}
