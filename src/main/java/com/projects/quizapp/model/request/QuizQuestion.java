package com.projects.quizapp.model.request;

//import jakarta.validation.constraints.NotNull;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QuizQuestion {

    private Long id;

    @NotNull
    private String description;

    private int maxMarks;

    @NotNull
    private List<String> choices;

    @NotNull
    private List<Integer> answers;
}
