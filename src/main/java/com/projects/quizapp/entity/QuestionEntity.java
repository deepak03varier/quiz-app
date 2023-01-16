package com.projects.quizapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.projects.quizapp.entity.dialects.IntegerListUserType;
import com.projects.quizapp.entity.dialects.StringListUserType;
import com.projects.quizapp.model.request.QuizQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

//import jakarta.persistence.*;

@Entity
@Table(name = "question_entity")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TypeDefs({@TypeDef(name = "StringListUserType", typeClass = StringListUserType.class),
           @TypeDef(name = "IntegerListUserType", typeClass = IntegerListUserType.class)})
public class QuestionEntity {

    @Id
    private Long id;

    private String description;

    private int maxMarks;

    @Type(type = "StringListUserType")
    private List<String> choices;

    @Type(type = "IntegerListUserType")
    private List<Integer> answers;

    @JsonBackReference
    @ManyToOne(targetEntity = QuizEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", referencedColumnName = "id",
                nullable = false)
    private QuizEntity quiz;

    public static QuestionEntity of(QuizQuestion question, QuizEntity quizEntity) {
        return QuestionEntity.builder()
                             .id(question.getId())
                             .description(question.getDescription())
                             .maxMarks(question.getMaxMarks())
                             .choices(question.getChoices())
                             .answers(question.getAnswers())
                             .quiz(quizEntity)
                             .build();
    }
}
