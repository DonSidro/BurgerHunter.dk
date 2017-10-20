package dk.burgerhunter.burgerhunter.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SidonKK on 29/05/2017.
 */

public class RegisterUser extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://172.104.136.15/api/v1/register";
    private Map<String, String> params;

    public RegisterUser(String name, String email, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("navn", name);
        params.put("email", email);
        params.put("password", password);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
