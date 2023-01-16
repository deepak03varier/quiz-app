package com.projects.quizapp.model.request;

//import jakarta.validation.constraints.NotNull;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QuizRequest {

    private Long id;

    @NotNull(message = "Quiz name required.")
    private String name;

    @NotNull(message = "Questions required.")
    private List<QuizQuestion> questions;
}
