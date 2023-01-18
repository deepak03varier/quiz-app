package com.projects.quizapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class QuizSubmitRequest {

    @NotNull(message = "Quiz id required to submit response")
    private Long quizId;

    @NotNull
    private String submittedBy;

    @NotNull
    @JsonProperty("question_response_map")
    private Map<Long, List<Integer>> quizQuestionToResponseMap;
}
