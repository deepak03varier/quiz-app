package com.projects.quizapp.repository;

import com.projects.quizapp.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Long> {

    @Query(value = "SELECT max(quiz.id) FROM QuizEntity quiz")
    Optional<Long> findQuizWithLargestQuizId();
}
