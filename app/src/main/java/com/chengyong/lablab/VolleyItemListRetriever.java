package com.chengyong.lablab;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.session.PlaybackState;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VolleyItemListRetriever implements VolleyItemImageResponse, VolleyJSONObjectResponse{
    private String mUrl;
    private MutableLiveData<ArrayList<Item>> mItemsData;
    private ArrayList<Item> mItems;
    private RequestQueue mQueue;

    public VolleyItemListRetriever(String pUrl, Context pContext)
    {
        mUrl = pUrl;
        mQueue = Volley.newRequestQueue(pContext);
        Log.i("VolleyItemListRetriever","constructor");
    }

    public LiveData<ArrayList<Item>> getItems(){
        Log.i("VolleyItemListRetriever","getItems ");
        mItemsData = new MutableLiveData<ArrayList<Item>>();
        CustomJSONObjectRequest request = new CustomJSONObjectRequest(Request.Method.GET, mUrl, null, "ItemListJSON", this);
        mQueue.add(request.getJsonObjectRequest());
        return mItemsData;
    }


    @Override
    public void onResponse(Bitmap pImage, Item pItem) {
        Log.i("VolleyItemListRetriever","Image retriever for "+pItem.getTitle());
        pItem.setImage(pImage);
        mItemsData.setValue(mItems);
    }

    @Override
    public void onResponse(JSONObject pObject, String pTag) {
        Log.i("VolleyItemListRetriever","jsonobject onresponse - "+pTag);
        mItems = parseJSONResponse(pObject);
        mItemsData.setValue(mItems);
    }

    @Override
    public void onError(VolleyError error, String pTag) {
        Log.i("VolleyItemListRetriever","onerror:"+pTag);
    }

    private ArrayList<Item> parseJSONResponse(JSONObject pResponse){
        ArrayList<Item> items = new ArrayList<>();
        try{
            JSONArray itemsArray = pResponse.getJSONArray("items");
            for(int i=0;i< itemsArray.length(); i++){
                JSONObject itemObject = itemsArray.getJSONObject(i);
                Item item = parseJSONItem(itemObject);
                items.add(item);
            }
        }
        catch(JSONException e){
            e.printStackTrace();
//            Log.i("VolleyItemListRetriever","onerror:");
        }
        return items;
    }

    private Item parseJSONItem(JSONObject pItemObject) throws org.json.JSONException{
        String title = pItemObject.getString("title");
        String link = pItemObject.getString("link");
        String date = pItemObject.getString("pubDate");
        String des = pItemObject.getString("description");
        String image = pItemObject.getString("image");

        Item item = new Item(title,link, date, des, image);

        CustomItemImageRequest itemImagRequest = new CustomItemImageRequest(
                item.getImageUrl(),
                item,
                this
        );
        mQueue.add(itemImagRequest.getmImageRequest());
        return item;
    }
}
