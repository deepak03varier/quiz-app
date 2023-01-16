package com.projects.quizapp.controller;

import com.projects.quizapp.model.request.QuizRequest;
import com.projects.quizapp.model.response.BaseResponse;
import com.projects.quizapp.service.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/quiz-app/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(final QuizService quizService){
        this.quizService=quizService;
    }

    @PostMapping("/quizzes")
    public BaseResponse createQuizzes(@RequestBody List<QuizRequest> quizzes){
        log.info("Received request to create quizzes : {}",quizzes);
        quizService.createQuizzes(quizzes);
        return BaseResponse.ofSuccess();
    }

    @GetMapping("/quiz")
    public BaseResponse getQuiz(@RequestParam("quiz_id") Long quizId){
        log.info("Received request to retrieve quiz for quiz id : {}",quizId);
        return BaseResponse.ofSuccess(quizService.getQuiz(quizId));
    }

    @PutMapping("/quiz")
    public BaseResponse updateQuiz(@RequestBody QuizRequest quiz){
        log.info("Received request to update quiz : {}",quiz);
        return BaseResponse.ofSuccess(quizService.updateQuiz(quiz));
    }

    @DeleteMapping("/quiz")
    public BaseResponse deleteQuiz(@RequestParam("quiz_id") Long quizId){
        log.info("Received request to delete quiz : {}",quizId);
        quizService.deleteQuiz(quizId);
        return BaseResponse.ofSuccess();
    }
}
