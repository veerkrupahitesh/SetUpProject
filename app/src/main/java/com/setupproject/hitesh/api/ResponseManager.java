package com.setupproject.hitesh.api;


import com.setupproject.hitesh.models.CityModel;
import com.setupproject.hitesh.models.CountryModel;
import com.setupproject.hitesh.models.LoginUserModel;
import com.setupproject.hitesh.models.StateModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ${hitesh} on 12/8/2016.
 */

public class ResponseManager {

    public static <T> Object parseResponse(String mResponse, RequestCode mRequestCode, Gson mGson) {

        Object object = null;

        try {

            JSONObject jsonObject = new JSONObject(mResponse);

            switch (mRequestCode) {

                case clientInsert:

                    object = mGson.fromJson(jsonObject.getJSONObject("ClientInsert").toString(), mRequestCode.getLocalClass());

                    LoginUserModel.setLoginCredentials(jsonObject.getJSONObject("ClientInsert").toString());
                    //PrefHelper.getInstance().setString(PrefHelper.CLIENT_CREDENTIALS, jsonObject.getJSONObject("ClientInsert").toString());
                    break;

                case GetUser:
                    object = mGson.fromJson(jsonObject.getJSONObject("GetUser").toString(), mRequestCode.getLocalClass());
                    LoginUserModel.setLoginCredentials(jsonObject.getJSONObject("GetUser").toString());
                    //  PrefHelper.getInstance().setString(PrefHelper.CLIENT_CREDENTIALS, jsonObject.getJSONObject("GetUser").toString());
                    break;

                case ClientUpdate:

                    object = mGson.fromJson(jsonObject.getJSONObject("ClientUpdate").toString(), mRequestCode.getLocalClass());
                    LoginUserModel.setLoginCredentials(jsonObject.getJSONObject("ClientUpdate").toString());
                    // PrefHelper.getInstance().setString(PrefHelper.CLIENT_CREDENTIALS, jsonObject.getJSONObject("ClientUpdate").toString());
                    break;

                case GetCountry:

                    object = mGson.fromJson(jsonObject.getJSONArray("GetCountry").toString(), new TypeToken<ArrayList<CountryModel>>() {
                    }.getType());
                    break;

                case GetState:

                    object = mGson.fromJson(jsonObject.getJSONArray("GetState").toString(), new TypeToken<ArrayList<StateModel>>() {
                    }.getType());
                    break;

                case GetCity:

                    object = mGson.fromJson(jsonObject.getJSONArray("GetCity").toString(), new TypeToken<ArrayList<CityModel>>() {
                    }.getType());
                    break;

                case ForgotPassword:

                    object = mGson.fromJson(jsonObject.getJSONObject("ForgotPassword").toString(), mRequestCode.getLocalClass());
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }
}
