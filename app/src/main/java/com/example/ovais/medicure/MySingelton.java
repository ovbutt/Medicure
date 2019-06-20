package com.example.ovais.medicure;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ovais Butt on 4/1/2018.
 */

public class MySingelton {

    private static MySingelton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MySingelton (Context context)
    {
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MySingelton getInstance (Context context)
    {
        if(mInstance == null)
        {
            mInstance = new MySingelton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }

        return requestQueue;
    }
    public void addToReuestQueue (Request request)
    {
        requestQueue.add(request);
    }
}
