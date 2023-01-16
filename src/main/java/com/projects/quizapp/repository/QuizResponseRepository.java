package com.projects.quizapp.repository;

import com.projects.quizapp.entity.QuizResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResponseRepository extends JpaRepository<QuizResponseEntity, Long> {

    @Query(value = "SELECT max(response.id) FROM QuizResponseEntity response")
    Optional<Long> findResponseWithLargestId();

    Optional<List<QuizResponseEntity>> findAllByQuizId(Long quizId);
}
