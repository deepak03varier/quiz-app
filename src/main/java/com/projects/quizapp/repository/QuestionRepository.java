package com.projects.quizapp.repository;

import com.projects.quizapp.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    @Query(value = "SELECT max(question.id) FROM QuestionEntity question")
    Optional<Long> findQuestionWithLargestQuestionId();

    @Query(value = "SELECT question FROM QuestionEntity question where question.quiz.id =?1 and question.id in (?2)")
    Optional<List<QuestionEntity>> findQuestionsByQuizIdAndIdsIn(Long quizId, Collection<Long> ids);
}
