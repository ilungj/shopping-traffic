package com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.service;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ilungj.android.shoppingtraffic.Application;

/**
 * Created by Il Ung on 1/16/2017.
 */

public class RequestSingleton {

    public static RequestSingleton instance = null;
    private RequestQueue requestQueue;

    public RequestSingleton() {
        requestQueue = Volley.newRequestQueue(Application.getContext());
    }

    public static RequestSingleton getInstance() {
        if(instance == null) {
            instance = new RequestSingleton();
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
