package com.projects.quizapp.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.projects.quizapp.entity.dialects.ObjectType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_response_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TypeDefs({@TypeDef(name = "ObjectType", typeClass = ObjectType.class)})
public class QuizResponseEntity {

    @Id
    private Long id;

    private Long quizId;

    private String userName;

    private LocalDateTime submittedAt;

    private Integer score;

    @Type(type = "ObjectType")
    private Object responseList;
}
