package com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ilungj.android.shoppingtraffic.CustomRecyclerAdapter;
import com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.service.Stores;
import com.ilungj.android.shoppingtraffic.R;
import com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.service.RequestSingleton;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Il Ung on 1/3/2017.
 */

public class SubFragment extends CustomFragment {

    Context context;
    List<Stores> storesList;
    RecyclerView recyclerView;
    CustomRecyclerAdapter customRecyclerAdapter;
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();

        setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub, container, false);
        setObjectList();
        //customRecyclerAdapter = new CustomRecyclerAdapter(storesList);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.bringToFront();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabLayout);
        tabLayout.bringToFront();
        //recyclerView.setAdapter(customRecyclerAdapter);

        return view;
    }

    private void setObjectList() {

        Log.d("SUBFRAGMENT", "SETOBJECTLIST CALLED");

        RequestQueue queue = RequestSingleton.getInstance().getRequestQueue();
        String url = "http://www.ilungj.com/getstores.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                storesList = Arrays.asList(gson.fromJson(response, Stores[].class));

                customRecyclerAdapter = new CustomRecyclerAdapter(context, storesList, progressBar);
                recyclerView.setAdapter(customRecyclerAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);
    }
}
