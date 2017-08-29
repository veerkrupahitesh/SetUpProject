package com.setupproject.hitesh.models;

import com.setupproject.hitesh.api.RestClient;
import com.setupproject.hitesh.helper.PrefHelper;

import java.io.Serializable;

/**
 * Created by VEER7 on 6/20/2017.
 */

public class LoginUserModel implements Serializable {

    public static LoginUserModel getLoginUserModel() {

        return RestClient.getGsonInstance().fromJson(PrefHelper.getInstance().getString(PrefHelper.CLIENT_CREDENTIALS, ""), LoginUserModel.class);
//        return (LoginUserModel) stringToObject(PrefHelper.getInstance().getString(PrefHelper.CLIENT_CREDENTIALS, ""));
    }

    public static void setLoginCredentials(String loginCredentials) {

        PrefHelper.getInstance().setString(PrefHelper.CLIENT_CREDENTIALS, loginCredentials);
    }
}
