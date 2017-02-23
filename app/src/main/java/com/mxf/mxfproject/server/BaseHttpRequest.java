package com.mxf.mxfproject.server;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mxf on 17/2/15.
 */

public class BaseHttpRequest {

    private Class mclass;

    public  BaseHttpRequest(Class className){
        this.mclass = className;

    }

    public Class getMclass(){
        return this.mclass;
    }

    public String genUrl(){
        return null;
    }

    public String genMethod(){
        return "POST";
    }

    public JSONObject genParams() throws JSONException {
        JSONObject json = new JSONObject();
        return json;
    }



}
