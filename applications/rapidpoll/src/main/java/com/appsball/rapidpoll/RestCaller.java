package com.appsball.rapidpoll;

import android.content.Context;

import com.appsball.rapidpoll.commons.communication.request.PollDetailsRequest;
import com.appsball.rapidpoll.commons.communication.request.PollResultRequest;
import com.appsball.rapidpoll.commons.communication.request.PollsRequest;
import com.appsball.rapidpoll.commons.communication.request.RegisterRequest;
import com.appsball.rapidpoll.commons.communication.request.SearchPollRequest;
import com.appsball.rapidpoll.commons.communication.request.UpdatePollStateRequest;
import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderKey;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderType;
import com.appsball.rapidpoll.commons.communication.response.PollsResponse;
import com.appsball.rapidpoll.commons.communication.response.RegisterResponse;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.model.PollState;
import com.orhanobut.logger.Logger;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.WaspError;

import java.util.List;

import static com.appsball.rapidpoll.commons.communication.request.DefaultRequestBuilders.createDoPollRequest;
import static com.appsball.rapidpoll.commons.communication.request.DefaultRequestBuilders.createManagePollRequest;
import static com.appsball.rapidpoll.commons.communication.request.RegisterRequest.registerRequest;

public class RestCaller {
    public static final String TEST_POLL_ID = "2";
    public static final String TEST_USER_ID = "11E584B41C1E65089E7502000029BDFD";

    public RapidPollRestService service;

    public RestCaller(Context context) {

        service = RapidPollRestService.createRapidPollRestService(context);
    }

    private SearchPollRequest createSearchPollRequest() {
        SearchPollRequest.Builder builder = SearchPollRequest.builder();
        builder.withUserId(TEST_USER_ID);
        builder.withListType(ListType.ALL);
        builder.withSearchItem("west");
        builder.withOrderKey(OrderKey.TITLE);
        builder.withOrderType(OrderType.DESC);
        builder.withPage("1");
        builder.withPageSize("25");
        return builder.build();
    }

    private PollResultRequest createPollResultRequest() {
        return PollResultRequest.builder().withPollId(TEST_POLL_ID).withUserId(TEST_USER_ID).build();
    }

    private PollDetailsRequest createPollDetailsRequest() {
        return PollDetailsRequest.builder().withPollId(TEST_POLL_ID).withUserId(TEST_USER_ID).build();
    }

    public void updatePollState() {
        service.updatePollState(createUpdatePollStateRequest(), new Callback<ResponseContainer<Object>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<Object> objectResponseContainer) {
                Logger.i("updatepoll response", objectResponseContainer);
            }

            @Override
            public void onError(WaspError error) {
                Logger.e("updatepoll response", error);
            }
        });
    }

    private UpdatePollStateRequest createUpdatePollStateRequest() {
        UpdatePollStateRequest.Builder builder = UpdatePollStateRequest.builder();
        builder.withUserId(TEST_USER_ID);
        builder.withPollId(TEST_POLL_ID);
        builder.withPollState(PollState.PUBLISHED);
        return builder.build();
    }

    public void searchPoll() {
        service.searchPoll(createSearchPollRequest(), new Callback<ResponseContainer<List<PollsResponse>>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<List<PollsResponse>> listResponseContainer) {
                Logger.wtf("wtf0");
            }

            @Override
            public void onError(WaspError error) {
                Logger.e("searchPoll response", error);
            }
        });
    }

    public void getPollResult() {
        service.pollResult(createPollResultRequest(), new Callback<ResponseContainer<PollResultResponse>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<PollResultResponse> pollResultResponseResponseContainer) {
                Logger.i("getPollResult response", pollResultResponseResponseContainer);
            }

            @Override
            public void onError(WaspError error) {
                Logger.e("getPollResult response", error);
            }
        });
    }

    public void doPoll() {
        service.doPoll(createDoPollRequest(), new Callback<String>() {
            @Override
            public void onSuccess(Response response, String objectResponseContainer) {
                Logger.i("doPoll response", objectResponseContainer);
            }

            @Override
            public void onError(WaspError error) {
                Logger.e("doPoll response", error);
            }
        });
    }

    public void getPollDetails() {
        service.pollDetails(createPollDetailsRequest(), new Callback<ResponseContainer<PollDetailsResponse>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<PollDetailsResponse> pollDetailsResponseModelResponseContainer) {
                Logger.i("getPollDetails response", pollDetailsResponseModelResponseContainer);
            }

            @Override
            public void onError(WaspError error) {
                Logger.e("getPollDetails response", error);

            }
        });
    }

    private PollsRequest createPollsRequest() {
        PollsRequest.Builder builder = PollsRequest.builder();
        builder.withUserId(TEST_USER_ID);
        builder.withListType(ListType.ALL);
        builder.withOrderKey(OrderKey.TITLE);
        builder.withOrderType(OrderType.DESC);
        builder.withPage("1");
        builder.withPageSize("25");
        return builder.build();
    }

    public void createPoll() {
        service.managePoll(createManagePollRequest(), new Callback<ResponseContainer<Object>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<Object> objectResponseContainer) {
                Logger.i("createPoll response", objectResponseContainer);
            }

            @Override
            public void onError(WaspError error) {
                Logger.e("createPoll response", error);

            }
        });
    }

    public void register() {
        RegisterRequest registerRequest = registerRequest("sdef_626");
        service.registerUser(registerRequest, new Callback<ResponseContainer<RegisterResponse>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<RegisterResponse> registerResponseResponseContainer) {
                Logger.i("register response", registerResponseResponseContainer);
            }

            @Override
            public void onError(WaspError error) {
                Logger.e("register response", error);
            }
        });
    }

    public void getPolls() {
        service.getPolls(createPollsRequest(),
                         new Callback<ResponseContainer<List<PollsResponse>>>() {
                             @Override
                             public void onSuccess(Response response, ResponseContainer<List<PollsResponse>> listResponseContainer) {
                                 Logger.i("getPolls response", listResponseContainer);
                             }

                             @Override
                             public void onError(WaspError error) {
                                 Logger.e("getPolls response", error);

                             }
                         }
        );
    }
}
