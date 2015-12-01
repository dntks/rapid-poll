package com.appsball.rapidpoll.commons.communication.request.dopoll;

import java.util.List;

//{"question_id":"1","answers":[{"alternative_id":"1"}]},
public class DoPollQuestion {
    public final String question_id;
    public final List<DoPollAnswer> answers;

    private DoPollQuestion(String question_id, List<DoPollAnswer> answers) {
        this.question_id = question_id;
        this.answers = answers;
    }

    public static DoPollQuestion doPollQuestion(String question_id, List<DoPollAnswer> answers) {
        return new DoPollQuestion(question_id, answers);
    }
}
