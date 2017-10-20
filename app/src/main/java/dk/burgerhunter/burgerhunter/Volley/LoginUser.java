package dk.burgerhunter.burgerhunter.Volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SidonKK on 29/05/2017.
 */

public class LoginUser extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://172.104.136.15/api/v1/login";
    private Map<String, String> params;

    public LoginUser(String email, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
