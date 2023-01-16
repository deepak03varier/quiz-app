package com.projects.quizapp.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class QuizPlayerResponse {
    private Integer score;
}
