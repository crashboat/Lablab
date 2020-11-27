package com.chengyong.lablab;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class CustomItemImageRequest implements Response.Listener<Bitmap>, Response.ErrorListener{
    private VolleyItemImageResponse mVolleyItemImageResponse;
    private Item mItem;
    private ImageRequest mImageRequest;

    public CustomItemImageRequest(String pUrl,
                                  Item pItem,
                                  VolleyItemImageResponse pVolleyItemImageResponse){
        mVolleyItemImageResponse = pVolleyItemImageResponse;
        mItem = pItem;
        mImageRequest = new ImageRequest(
                pUrl,
                this,
                0,
                0,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.RGB_565,
                this
        );
    }

    @Override
    public void onErrorResponse(VolleyError pError) {
        mVolleyItemImageResponse.onError(pError, mItem.getTitle());
    }

    @Override
    public void onResponse(Bitmap pResponse) {
        mVolleyItemImageResponse.onResponse(pResponse, mItem);

    }

    public ImageRequest getmImageRequest(){return mImageRequest;}
}
