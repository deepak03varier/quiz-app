package com.projects.quizapp.service.impl;

import com.projects.quizapp.entity.QuizEntity;
import com.projects.quizapp.exception.exceptions.BadRequestException;
import com.projects.quizapp.model.request.QuizRequest;
import com.projects.quizapp.repository.QuestionRepository;
import com.projects.quizapp.repository.QuizRepository;
import com.projects.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public QuizServiceImpl(final QuizRepository quizRepository, final QuestionRepository questionRepository) {
        this.quizRepository=quizRepository;
        this.questionRepository=questionRepository;
    }

    @Override
    public void createQuizzes(List<QuizRequest> quizzes) {
        quizRepository.saveAll(
                quizzes.stream().map(quizRequest -> QuizEntity.of(generateId(quizRequest), "TODO")).collect(Collectors.toList()));
    }

    @Override
    public QuizEntity getQuiz(Long quizId) {
        return quizRepository.findById(quizId).orElseThrow(() -> new BadRequestException("No quiz found with the quiz id"));
    }

    @Override
    public QuizEntity updateQuiz(QuizRequest quiz) {
        QuizEntity quizEntity = quizRepository.findById(quiz.getId()).orElseThrow(() -> new BadRequestException("No quiz found with the quiz id"));
        if(!quizEntity.isCanBeUpdated()) throw new BadRequestException("Quiz cannot be updated once shared");
        return quizRepository.save(QuizEntity.of(quiz,quizEntity.getCreatedBy()));
    }

    @Override
    public void deleteQuiz(Long quizId) {
        quizRepository.delete(quizRepository.findById(quizId).orElseThrow(() -> new BadRequestException("No quiz found with the quiz id")));
    }

    private QuizRequest generateId(QuizRequest quizRequest) {
        Long latestQuizId = quizRepository.findQuizWithLargestQuizId().orElse(1L);
        AtomicReference<Long> latestQuestionId =
                new AtomicReference<>(questionRepository.findQuestionWithLargestQuestionId().orElse(1L));
        quizRequest.getQuestions().forEach(question -> {
            latestQuestionId.set(latestQuestionId.get() + 1);
            question.setId(latestQuestionId.get());
        });
        quizRequest.setId(latestQuizId+1);
        return quizRequest;
    }
}
