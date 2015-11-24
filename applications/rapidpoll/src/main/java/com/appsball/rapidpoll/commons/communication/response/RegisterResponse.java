package com.appsball.rapidpoll.commons.communication.response;

import java.util.List;

/**
 * { "result":
 *       {"user_id":"11E5898E579381089E7502000029BDFD"},
 *         "status":"SUCCESS",
 *         "messages":{}
 *  }
 */
public class RegisterResponse{

    String user_id;

    public String getUserId() {
        return user_id;
    }
}
