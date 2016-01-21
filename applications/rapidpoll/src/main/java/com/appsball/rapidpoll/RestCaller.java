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
import com.appsball.rapidpoll.commons.communication.response.ManagePollResponse;
import com.appsball.rapidpoll.commons.communication.response.PollsResponse;
import com.appsball.rapidpoll.commons.communication.response.RegisterResponse;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.communication.service.ResponseCallback;
import com.appsball.rapidpoll.commons.communication.service.ResponseContainerCallback;
import com.appsball.rapidpoll.commons.model.PollState;
import com.orhanobut.logger.Logger;

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
        service.updatePollState(createUpdatePollStateRequest(), new ResponseCallback() {
            @Override
            public void onSuccess() {
                Logger.i("register response");
            }

            @Override
            public void onFailure() {
                Logger.e("register failure");

            }

            @Override
            public void onError(String errorMessage) {
                Logger.e("register response", errorMessage);
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
        service.searchPoll(createSearchPollRequest(), new ResponseContainerCallback<List<PollsResponse>>() {
            @Override
            public void onSuccess(List<PollsResponse> pollDetailsResponse) {
                Logger.e("getPollDetails response", pollDetailsResponse);
            }

            @Override
            public void onFailure() {
                Logger.i("getPollDetails response", "wrong code");
            }

            @Override
            public void onError(String errorMessage) {
                Logger.e("getPollDetails response", errorMessage);
            }
        });
    }

    public void getPollResult() {
        service.pollResult(createPollResultRequest(), new ResponseContainerCallback<PollResultResponse>() {
            @Override
            public void onSuccess(PollResultResponse pollDetailsResponse) {
                Logger.e("getPollDetails response", pollDetailsResponse);
            }

            @Override
            public void onFailure() {
                Logger.i("getPollDetails response", "wrong code");
            }

            @Override
            public void onError(String errorMessage) {
                Logger.e("getPollDetails response", errorMessage);
            }
        });
    }

    public void doPoll() {
        service.doPoll(createDoPollRequest(), new ResponseCallback() {
            @Override
            public void onSuccess() {
                Logger.i("register response");
            }

            @Override
            public void onFailure() {
                Logger.e("register failure");

            }

            @Override
            public void onError(String errorMessage) {
                Logger.e("register response", errorMessage);
            }
        });
    }

    public void getPollDetails() {
        service.pollDetails(createPollDetailsRequest(), new ResponseContainerCallback<PollDetailsResponse>() {
            @Override
            public void onSuccess(PollDetailsResponse pollDetailsResponse) {
                Logger.e("getPollDetails response", pollDetailsResponse);
            }

            @Override
            public void onFailure() {
                Logger.i("getPollDetails response", "wrong code");
            }

            @Override
            public void onError(String errorMessage) {
                Logger.e("getPollDetails response", errorMessage);
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
        service.managePoll(createManagePollRequest(), new ResponseContainerCallback<ManagePollResponse>() {
            @Override
            public void onSuccess(ManagePollResponse response) {
                Logger.i("register response", response);
            }

            @Override
            public void onFailure() {
                Logger.e("register failure");

            }

            @Override
            public void onError(String errorMessage) {
                Logger.e("register response", errorMessage);
            }
        });
    }

    public void register() {
        RegisterRequest registerRequest = registerRequest("sdef_626");
        service.registerUser(registerRequest, new ResponseContainerCallback<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse response) {
                Logger.i("register response", response);
            }

            @Override
            public void onFailure() {
                Logger.e("register failure");

            }

            @Override
            public void onError(String errorMessage) {
                Logger.e("register response", errorMessage);
            }
        });
    }

    public void getPolls() {
        service.getPolls(createPollsRequest(), new ResponseContainerCallback<List<PollsResponse>>() {
                    @Override
                    public void onSuccess(List<PollsResponse> response) {
                        Logger.i("register response", response);
                    }

                    @Override
                    public void onFailure() {
                        Logger.e("register failure");

                    }

                    @Override
                    public void onError(String errorMessage) {
                        Logger.e("register response", errorMessage);
                    }
                }
        );
    }
}
