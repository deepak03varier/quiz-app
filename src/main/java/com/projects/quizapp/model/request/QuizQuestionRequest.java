package com.projects.quizapp.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class QuizQuestionRequest {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private int maxMarks;

    @NotNull
    private List<String> choices;

    @NotNull
    private List<Integer> answers;
}
