package com.projects.quizapp.controller;

import com.projects.quizapp.model.request.QuizSubmitRequest;
import com.projects.quizapp.model.response.BaseResponse;
import com.projects.quizapp.service.QuizSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/quiz-submission/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuizSubmissionController {

    private final QuizSubmissionService quizSubmissionService;

    @Autowired
    public QuizSubmissionController(QuizSubmissionService quizSubmissionService) {
        this.quizSubmissionService = quizSubmissionService;
    }

    @PostMapping("/submit")
    public BaseResponse submitResponse(@RequestBody QuizSubmitRequest submitRequest, Principal principal) {
        return BaseResponse.ofSuccess(quizSubmissionService.submitResponse(submitRequest, principal.getName()));
    }

    @GetMapping("/list")
    public BaseResponse getQuizSubmissions(@RequestParam("quiz_id") Long quizId) {
        return BaseResponse.ofSuccess(quizSubmissionService.getQuizSubmissions(quizId));
    }

    @GetMapping("/quiz/play")
    public BaseResponse getQuizForPlayer(@RequestParam("quiz_id") Long quizId) {
        return BaseResponse.ofSuccess(quizSubmissionService.getQuizForPlayer(quizId));
    }
}
