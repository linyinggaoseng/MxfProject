package com.mxf.mxfproject.server;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mxf on 17/2/15.
 */

public class LogInRequest extends BaseHttpRequest {

    public String userName;
    public String passwdWord;

    public LogInRequest(Class className) {
        super(className);
    }

    @Override
    public String genUrl() {
        return "http://192.168.200.165:8877/api/v1" + "/user/login";
    }

    @Override
    public JSONObject genParams() throws JSONException {
        JSONObject json = super.genParams();
        if (this.userName != null){
            json.put("name",this.userName);
        }
        if (this.passwdWord != null) {
            json.put("password", this.passwdWord);
        }
        return json;
    }



}
