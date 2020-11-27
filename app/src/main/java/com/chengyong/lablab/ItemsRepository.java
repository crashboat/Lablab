package com.chengyong.lablab;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemsRepository {
    private static ItemsRepository sItemsRepository;

    private Context mApplicationContext;
    private LiveData<ArrayList<Item>> mItems;
    private LiveData<Item>mSelectedItem;

    private ItemsRepository(Context pApplicationContext){
        this.mApplicationContext = pApplicationContext;
    }

    public static ItemsRepository getInstance(Context pApplicationContext){
        if(sItemsRepository==null){
            sItemsRepository = new ItemsRepository(pApplicationContext);
        }
        return sItemsRepository;
    }

    public LiveData<ArrayList<Item>>loadItemsFromJSON(){
        RequestQueue queue = Volley.newRequestQueue(mApplicationContext);
        String url =  "https://www.goparker.com/600096/index.json";
        final MutableLiveData<ArrayList<Item>>mutableItems = new MutableLiveData<>();

        JsonObjectRequest jsonobjectRequest = new JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject response) {
                    ArrayList<Item> items = parseJSONResponse(response);
                    mutableItems.setValue(items);
                    mItems = mutableItems;
                    Log.i("Activity Lifecycle","loadItemsFromJSON, onresponse");
                }
                },
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Log.i("Activity Lifecycle","loadItemsFromJSON, onerrorresponse");
                    String errorResponse = "That didn't work!";
                }
            });

        queue.add(jsonobjectRequest);
        return mutableItems;
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
        }
        return items;
    }
    private Item parseJSONItem(JSONObject pItemObject) throws org.json.JSONException{
        String title = pItemObject.getString("title");
        String link = pItemObject.getString("link");
        String date = pItemObject.getString("pubDate");
        String des = pItemObject.getString("description");
        String image = pItemObject.getString("image");

        Item item = new Item(title,link, date, des);
        item.setImageUrl(image);
        return item;
    }

    public LiveData<ArrayList<Item>>getItems(){
        if(mItems==null){
            mItems = loadItemsFromJSON();
        }
        return mItems;
    }

    public LiveData<Item>getItem(int pItemIndex){
        LiveData<Item>transformedItem = Transformations.switchMap(mItems,items->{
            MutableLiveData<Item> itemData = new MutableLiveData<>();
            Item item = items.get(pItemIndex);
            itemData.setValue(item);
            loadImage(item.getImageUrl(),itemData);
            return itemData;
        });
        mSelectedItem = transformedItem;
        return mSelectedItem;
    }

    public void loadImage(String pUrl, MutableLiveData<Item> pItemData){
        RequestQueue queue = Volley.newRequestQueue(mApplicationContext);
        final MutableLiveData<Item> mutableItem = pItemData;
        ImageRequest imageRequest = new ImageRequest(
                pUrl,new Response.Listener<Bitmap>(){
            @Override
            public void onResponse(Bitmap bitmap){
                Item item = mutableItem.getValue();
                item.setImage(bitmap);
                mutableItem.setValue(item);
            }
        },
                0, 0,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                String errorResponse = "That didn't work!";
            }
        });
        queue.add(imageRequest);
    }

}

