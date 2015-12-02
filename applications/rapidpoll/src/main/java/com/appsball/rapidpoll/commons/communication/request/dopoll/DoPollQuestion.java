package com.appsball.rapidpoll.commons.communication.request.dopoll;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class DoPollQuestion {
    public final String question_id;
    public final List<DoPollAnswer> answers;

    private DoPollQuestion(String questionId, List<DoPollAnswer> answers) {
        this.question_id = notNull(questionId, "questionId must not be null");
        this.answers = notNull(answers, "answers must not be null");
    }

    public static DoPollQuestion doPollQuestion(String questionId, List<DoPollAnswer> answers) {
        return new DoPollQuestion(questionId, answers);
    }
}
