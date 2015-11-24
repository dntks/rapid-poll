package com.appsball.rapidpoll.commons.communication.response;

import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class DefaultResponseBuilders {

    private final static String SUCCESS = "SUCCESS";

    public static ResponseContainer<List<PollResponseModel>> createPollsResponse() {
        ResponseContainer<List<PollResponseModel>> responseContainer = new ResponseContainer<>();
        responseContainer.messages = newArrayList();
        responseContainer.status = SUCCESS;
        responseContainer.result = createDefaultPollResponseList();
        return responseContainer;
    }

    private static List<PollResponseModel> createDefaultPollResponseList() {

        PollResponseModel pollResponseModel1 = new PollResponseModel();
        pollResponseModel1.id = 12;
        pollResponseModel1.name = "West End Christmas";
        pollResponseModel1.isPublic = 1;
        pollResponseModel1.anonymous = 0;
        pollResponseModel1.allow_comment = 1;
        pollResponseModel1.expiration_date = "2015-11-27 00:00:00";
        pollResponseModel1.owner_id = "11E58A684CDE15E49E7502000029BDFD";
        pollResponseModel1.state = "DRAFT";
        pollResponseModel1.publication_date = null;
        pollResponseModel1.number_of_questions = 2;
        pollResponseModel1.number_of_answered_questions_by_the_user = 0;
        pollResponseModel1.number_of_votes = 0;

        PollResponseModel pollResponseModel2 = new PollResponseModel();
        pollResponseModel2.id = 2;
        pollResponseModel2.name = "MOM 2015";
        pollResponseModel2.isPublic = 1;
        pollResponseModel2.anonymous = 1;
        pollResponseModel2.allow_comment = 1;
        pollResponseModel2.expiration_date = "2015-11-20 00:00:00";
        pollResponseModel2.owner_id = "31000000000000000000000000000000";
        pollResponseModel2.state = "PUBLISHED";
        pollResponseModel2.publication_date = "2015-11-06 13:27:00";
        pollResponseModel2.number_of_questions = 3;
        pollResponseModel2.number_of_answered_questions_by_the_user = 3;
        pollResponseModel2.number_of_votes = 5;

        PollResponseModel pollResponseModel3 = new PollResponseModel();
        pollResponseModel3.id = 1;
        pollResponseModel3.name = "Avon 2015";
        pollResponseModel3.isPublic = 1;
        pollResponseModel3.anonymous = 1;
        pollResponseModel3.allow_comment = 1;
        pollResponseModel3.expiration_date = "2015-11-20 00:00:00";
        pollResponseModel3.owner_id = "31000000000000000000000000000000";
        pollResponseModel3.state = "DRAFT";
        pollResponseModel3.publication_date = null;
        pollResponseModel3.number_of_questions = 4;
        pollResponseModel3.number_of_answered_questions_by_the_user = 4;
        pollResponseModel3.number_of_votes = 3;

        return newArrayList(pollResponseModel1, pollResponseModel2, pollResponseModel3);
    }


    public static ResponseContainer<PollDetailsResponseModel> createPollDetailsResponse() {
        ResponseContainer<PollDetailsResponseModel> responseContainer = new ResponseContainer<>();
        responseContainer.messages = newArrayList();
        responseContainer.status = SUCCESS;
        responseContainer.result = createDefaultPollDetailsResponse();
        return responseContainer;
    }

    private static PollDetailsResponseModel createDefaultPollDetailsResponse() {
        PollDetailsResponseModel pollDetailsResponseModel = new PollDetailsResponseModel();
        pollDetailsResponseModel.id = 2;
        pollDetailsResponseModel.name = "MOM 2015";
        pollDetailsResponseModel.isPublic = 1;
        pollDetailsResponseModel.anonymous = 1;
        pollDetailsResponseModel.allow_comment = 1;
        pollDetailsResponseModel.expiration_date = "2015-11-20 00:00:00";
        pollDetailsResponseModel.owner_id = "31000000000000000000000000000000";
        pollDetailsResponseModel.state = "PUBLISHED";
        pollDetailsResponseModel.publication_time = "2015-11-06 13:27:00";
        pollDetailsResponseModel.questions = createQuestions();
        return pollDetailsResponseModel;
    }

    private static List<QuestionResponseModel> createQuestions() {
        QuestionResponseModel questionResponseModel1 = new QuestionResponseModel();
        questionResponseModel1.question_id = 5;
        questionResponseModel1.multichoice = 0;
        questionResponseModel1.question = "Rate our mall?";
        questionResponseModel1.alternatives =
                newArrayList(createAlternative(17, "Excellent"),
                        createAlternative(18, "Good"),
                        createAlternative(19, "Neutral"),
                        createAlternative(20, "Bad")
                );
        QuestionResponseModel questionResponseModel2 = new QuestionResponseModel();

        questionResponseModel2.question_id = 6;
        questionResponseModel2.multichoice = 0;
        questionResponseModel2.question = "Which the best shop in the 1st floor?";
        questionResponseModel2.alternatives =
                newArrayList(createAlternative(21, "Drogerie Markt"),
                        createAlternative(22, "Tesco"),
                        createAlternative(23, "Armani Factory Store"),
                        createAlternative(24, "Media Markt")
                );
        QuestionResponseModel questionResponseModel3 = new QuestionResponseModel();
        questionResponseModel3.question_id = 7;
        questionResponseModel3.multichoice = 1;
        questionResponseModel3.question = "Vote for our next new brand!";
        questionResponseModel3.alternatives =
                newArrayList(createAlternative(25, "Hugo Boss"),
                        createAlternative(26, "Zara"),
                        createAlternative(27, "Polo Sport"),
                        createAlternative(28, "Calvin Klein")
                );
        return newArrayList(questionResponseModel1, questionResponseModel2, questionResponseModel3);
    }

    private static AlternativeResponseModel createAlternative(int id, String name) {
        AlternativeResponseModel alternativeResponseModel = new AlternativeResponseModel();
        alternativeResponseModel.alternative_id = id;
        alternativeResponseModel.alternative_name = name;
        return alternativeResponseModel;
    }
}
