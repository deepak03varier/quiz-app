package com.projects.quizapp.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
//import jakarta.persistence.*;
import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "quiz_response_entity")
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class QuizResponseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long quizId;

    private String userName;

    private LocalDateTime submittedAt;

    private Integer score;

//    @MapKey
//    private Map<Long, List<Integer>> responseList;
}
