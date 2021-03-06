package hcmute.nhom13.myapplication.Volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.Map;

public class MyJsonArrayRequest extends JsonArrayRequest {

    private Map<String, String> mPostParams;

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mPostParams;
    }

    public MyJsonArrayRequest(String url, Map<String, String> postParams, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);

        this.mPostParams = postParams;
    }
}

