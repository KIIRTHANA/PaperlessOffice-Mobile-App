package hexageeks.daftar.backend;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ServerRequestQueue {

    private static ServerRequestQueue mInstance;
    private Context mContext;
    private RequestQueue mRequestQueue;

    private ServerRequestQueue(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized ServerRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ServerRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }
}