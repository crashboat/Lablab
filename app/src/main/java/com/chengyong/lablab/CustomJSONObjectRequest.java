package com.chengyong.lablab;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class CustomJSONObjectRequest implements Response.Listener<JSONObject>, Response.ErrorListener {


    private VolleyJSONObjectResponse mVolleyJSONObjectResponse;//this can be defiend as VolleyJSONObjectResponse too
    private String mTag;
    private JsonObjectRequest mJsonObjectRequest;


    public CustomJSONObjectRequest(int pMethod,
                                   String pUrl,
                                   JSONObject pJsonObject,
                                   String pTag,
                                   VolleyJSONObjectResponse pVolleyJSONObjectResponse){
        Log.i("CustomJSONObjectRequest","constructor");
        this.mVolleyJSONObjectResponse = pVolleyJSONObjectResponse;
        this.mTag = pTag;
        mJsonObjectRequest = new JsonObjectRequest(
                pMethod,
                pUrl,
                pJsonObject,
                this,
                this
        );
    }

    @Override
    public void onErrorResponse(VolleyError pError) {
        Log.i("CustomJSONObjectRequest","onErrorResponse");
        mVolleyJSONObjectResponse.onError(pError, mTag);
    }

    @Override
    public void onResponse(JSONObject pResponse) {
        Log.i("CustomJSONObjectRequest","onResponse");
        mVolleyJSONObjectResponse.onResponse(pResponse, mTag);
    }

    public JsonObjectRequest getJsonObjectRequest() {return mJsonObjectRequest;}

}
