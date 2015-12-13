package com.appsball.rapidpoll.commons.communication.response;

import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsAlternative;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsQuestion;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultAlternative;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultQuestion;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultResponse;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class DefaultResponseBuilders {

    private final static String SUCCESS = "SUCCESS";

    public static ResponseContainer<List<PollsResponse>> createPollsResponse() {
        ResponseContainer<List<PollsResponse>> responseContainer = new ResponseContainer<>();
        responseContainer.messages = newArrayList();
        responseContainer.status = SUCCESS;
        responseContainer.result = createDefaultPollResponseList();
        return responseContainer;
    }

    private static List<PollsResponse> createDefaultPollResponseList() {

        PollsResponse pollsResponse1 = new PollsResponse();
        pollsResponse1.id = 12;
        pollsResponse1.name = "West End Christmas";
        pollsResponse1.isPublic = 1;
        pollsResponse1.anonymous = 0;
        pollsResponse1.allow_comment = 1;
        pollsResponse1.expiration_date = "2015-11-27 00:00:00";
        pollsResponse1.owner_id = "11E58A684CDE15E49E7502000029BDFD";
        pollsResponse1.state = "DRAFT";
        pollsResponse1.publication_date = null;
        pollsResponse1.number_of_questions = 2;
        pollsResponse1.number_of_answered_questions_by_the_user = 0;
        pollsResponse1.number_of_votes = 0;

        PollsResponse pollsResponse2 = new PollsResponse();
        pollsResponse2.id = 2;
        pollsResponse2.name = "MOM 2015";
        pollsResponse2.isPublic = 1;
        pollsResponse2.anonymous = 1;
        pollsResponse2.allow_comment = 1;
        pollsResponse2.expiration_date = "2015-11-20 00:00:00";
        pollsResponse2.owner_id = "31000000000000000000000000000000";
        pollsResponse2.state = "PUBLISHED";
        pollsResponse2.publication_date = "2015-11-06 13:27:00";
        pollsResponse2.number_of_questions = 3;
        pollsResponse2.number_of_answered_questions_by_the_user = 3;
        pollsResponse2.number_of_votes = 5;

        PollsResponse pollsResponse3 = new PollsResponse();
        pollsResponse3.id = 1;
        pollsResponse3.name = "Avon 2015";
        pollsResponse3.isPublic = 1;
        pollsResponse3.anonymous = 1;
        pollsResponse3.allow_comment = 1;
        pollsResponse3.expiration_date = "2015-11-20 00:00:00";
        pollsResponse3.owner_id = "31000000000000000000000000000000";
        pollsResponse3.state = "DRAFT";
        pollsResponse3.publication_date = null;
        pollsResponse3.number_of_questions = 4;
        pollsResponse3.number_of_answered_questions_by_the_user = 4;
        pollsResponse3.number_of_votes = 3;

        return newArrayList(pollsResponse1, pollsResponse2, pollsResponse3);
    }


    public static ResponseContainer<PollDetailsResponse> createPollDetailsResponse() {
        ResponseContainer<PollDetailsResponse> responseContainer = new ResponseContainer<>();
        responseContainer.messages = newArrayList();
        responseContainer.status = SUCCESS;
        responseContainer.result = createDefaultPollDetailsResponse();
        return responseContainer;
    }

    private static PollDetailsResponse createDefaultPollDetailsResponse() {
        PollDetailsResponse pollDetailsResponse = new PollDetailsResponse();
        pollDetailsResponse.id = 2;
        pollDetailsResponse.name = "MOM 2015";
        pollDetailsResponse.isPublic = 1;
        pollDetailsResponse.anonymous = 1;
        pollDetailsResponse.allow_comment = 1;
        pollDetailsResponse.expiration_date = "2015-11-20 00:00:00";
        pollDetailsResponse.owner_id = "31000000000000000000000000000000";
        pollDetailsResponse.state = "PUBLISHED";
        pollDetailsResponse.publication_time = "2015-11-06 13:27:00";
        pollDetailsResponse.questions = createPollDetailsQuestions();
        return pollDetailsResponse;
    }

    private static List<PollDetailsQuestion> createPollDetailsQuestions() {
        PollDetailsQuestion pollDetailsQuestion1 = new PollDetailsQuestion();
        pollDetailsQuestion1.question_id = 5;
        pollDetailsQuestion1.multichoice = 0;
        pollDetailsQuestion1.question = "Rate our mall?";
        pollDetailsQuestion1.alternatives =
                newArrayList(createAlternative(17, "Excellent"),
                        createAlternative(18, "Good"),
                        createAlternative(19, "Neutral"),
                        createAlternative(20, "Bad")
                );
        PollDetailsQuestion pollDetailsQuestion2 = new PollDetailsQuestion();

        pollDetailsQuestion2.question_id = 6;
        pollDetailsQuestion2.multichoice = 0;
        pollDetailsQuestion2.question = "Which the best shop in the 1st floor?";
        pollDetailsQuestion2.alternatives =
                newArrayList(createAlternative(21, "Drogerie Markt"),
                        createAlternative(22, "Tesco"),
                        createAlternative(23, "Armani Factory Store"),
                        createAlternative(24, "Media Markt")
                );
        PollDetailsQuestion pollDetailsQuestion3 = new PollDetailsQuestion();
        pollDetailsQuestion3.question_id = 7;
        pollDetailsQuestion3.multichoice = 1;
        pollDetailsQuestion3.question = "Vote for our next new brand!";
        pollDetailsQuestion3.alternatives =
                newArrayList(createAlternative(25, "Hugo Boss"),
                        createAlternative(26, "Zara"),
                        createAlternative(27, "Polo Sport"),
                        createAlternative(28, "Calvin Klein")
                );
        return newArrayList(pollDetailsQuestion1, pollDetailsQuestion2, pollDetailsQuestion3);
    }

    private static PollDetailsAlternative createAlternative(int id, String name) {
        PollDetailsAlternative pollDetailsAlternative = new PollDetailsAlternative();
        pollDetailsAlternative.alternative_id = id;
        pollDetailsAlternative.alternative_name = name;
        return pollDetailsAlternative;
    }


    public static ResponseContainer<PollResultResponse> createPollResultResponse() {
        ResponseContainer<PollResultResponse> responseContainer = new ResponseContainer<>();
        responseContainer.messages = newArrayList();
        responseContainer.status = SUCCESS;
        responseContainer.result = createDefaultPollResultResponse();
        return responseContainer;
    }
    private static PollResultResponse createDefaultPollResultResponse() {
        PollResultResponse pollResultResponse = new PollResultResponse();
        pollResultResponse.id = "2";
        pollResultResponse.questions = createPollResultResponseQuestions();
        return pollResultResponse;
    }

    private static List<PollResultQuestion> createPollResultResponseQuestions() {
        PollResultQuestion question1 =
                createPollResultQuestion(5, "Rate our mall?", createPollResultAlternatives1());
        PollResultQuestion question2 =
                createPollResultQuestion(6, "Which the best shop in the 1st floor?", createPollResultAlternatives2());
        PollResultQuestion question3 =
                createPollResultQuestion(7, "Vote for our next new brand!", createPollResultAlternatives3());
        return newArrayList(question1, question2, question3);
    }

    private static PollResultQuestion createPollResultQuestion(long id,
                                                               String name,
                                                               List<PollResultAlternative> alternatives) {
        PollResultQuestion pollResultQuestion = new PollResultQuestion();
        pollResultQuestion.question_id = id;
        pollResultQuestion.question_name = name;
        pollResultQuestion.alternatives = alternatives;
        return pollResultQuestion;
    }

    private static List<PollResultAlternative> createPollResultAlternatives1() {
        PollResultAlternative pollResultAlternative1 = createPollResultAlternative(17, "Excellent", 4);
        PollResultAlternative pollResultAlternative2 = createPollResultAlternative(18, "Good", 5);
        PollResultAlternative pollResultAlternative3 = createPollResultAlternative(19, "Neutral", 3);
        PollResultAlternative pollResultAlternative4 = createPollResultAlternative(20, "Bad", 2);
        return newArrayList(pollResultAlternative1, pollResultAlternative2, pollResultAlternative3, pollResultAlternative4);
    }

    private static PollResultAlternative createPollResultAlternative(long id, String name, long count) {
        PollResultAlternative pollResultAlternative = new PollResultAlternative();
        pollResultAlternative.alternative_id = id;
        pollResultAlternative.alternative_name = name;
        pollResultAlternative.count = count;
        return pollResultAlternative;
    }

    private static List<PollResultAlternative> createPollResultAlternatives3() {
        PollResultAlternative pollResultAlternative1 = createPollResultAlternative(25, "Hugo Boss", 8);
        PollResultAlternative pollResultAlternative2 = createPollResultAlternative(26, "Zara", 9);
        PollResultAlternative pollResultAlternative3 = createPollResultAlternative(27, "Polo Sport", 7);
        PollResultAlternative pollResultAlternative4 = createPollResultAlternative(28, "Calvin Klein", 6);
        return newArrayList(pollResultAlternative1, pollResultAlternative2, pollResultAlternative3, pollResultAlternative4);
    }

    private static List<PollResultAlternative> createPollResultAlternatives2() {
        PollResultAlternative pollResultAlternative1 = createPollResultAlternative(21, "Drogerie Markt", 2);
        PollResultAlternative pollResultAlternative2 = createPollResultAlternative(22, "Tesco", 6);
        PollResultAlternative pollResultAlternative3 = createPollResultAlternative(23, "Armani Factory Store", 5);
        PollResultAlternative pollResultAlternative4 = createPollResultAlternative(24, "Media Markt", 1);
        return newArrayList(pollResultAlternative1, pollResultAlternative2, pollResultAlternative3, pollResultAlternative4);

    }
}
