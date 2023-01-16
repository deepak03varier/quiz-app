package com.projects.quizapp.repository;

import com.projects.quizapp.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    @Query(value = "SELECT max(question.id) FROM QuestionEntity question")
    Optional<Long> findQuestionWithLargestQuestionId();
}
