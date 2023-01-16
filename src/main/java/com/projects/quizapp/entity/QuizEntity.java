package com.projects.quizapp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.projects.quizapp.model.request.QuizRequest;
//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "quiz_entity")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class QuizEntity {

    @Id
    private Long id;

    private String name;

    private String createdBy;

    private boolean canBeUpdated = true;

    @OneToMany(
            targetEntity = QuestionEntity.class,
            mappedBy = "quiz",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<QuestionEntity> questions;

    public static QuizEntity of(QuizRequest request, String createdBy) {
        QuizEntity quizEntity = QuizEntity.builder()
                                          .id(request.getId())
                                          .name(request.getName())
                                          .createdBy(createdBy)
                                          .canBeUpdated(true)
                                          .build();
        quizEntity.setQuestions(request.getQuestions()
                                                  .stream()
                                                  .map(question -> QuestionEntity.of(question, quizEntity))
                                                  .collect(Collectors.toList()));
        return quizEntity;
    }
}
