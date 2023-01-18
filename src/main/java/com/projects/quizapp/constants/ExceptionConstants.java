package com.projects.quizapp.constants;

public class ExceptionConstants {

    public static String AUTHENTICATION_FAILED_ERROR_MESSAGE = "Authentication failed";

    public static String SECURITY_ISSUER_MISMATCH_GENERIC_ERROR_MESSAGE = "Issuer %s does not match cognito idp %s";

    public static String NO_QUIZ_FOUND_ERROR_MESSAGE = "No quiz found with the quiz id";

    public static String UPDATE_QUIZ_AFTER_SHARED_ERROR_MESSAGE = "Quiz cannot be updated once shared";

    public static String NO_SUBMISSIONS_FOUND_ERROR_MESSAGE = "No submissions found for this quiz id : ";

    public static String NO_QUESTION_FOUND_ERROR_MESSAGE = "No question found with the quiz id";
}
