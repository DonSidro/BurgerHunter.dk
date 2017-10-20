package dk.burgerhunter.burgerhunter.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SidonKK on 29/09/2017.
 */

public class ForgotPassword extends StringRequest {


    private static final String FORGOT_REQUEST_URL = "http://172.104.136.15/api/v1/forget";
    private Map<String, String> params;

    public ForgotPassword(String email, Response.Listener<String> listener) {
        super(Method.POST, FORGOT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
