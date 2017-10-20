package dk.burgerhunter.burgerhunter.Volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import dk.burgerhunter.burgerhunter.Helper.Burger;

/**
 * Created by SidonKK on 01/06/2017.
 */

public class RegisterBurger extends StringRequest {


    private static final String REGISTER_REQUEST_URL = "http://172.104.136.15/api/v1/register";
    private Map<String, String> params;

    public RegisterBurger(Burger burger, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("burger_navn", burger.getName());
        params.put("burger_rating", burger.getRating()+"");
        params.put("geolng", burger.getLongitude()+"");
        params.put("geolat", burger.getLatitude()+"");
        params.put("billede", burger.getImageString());
        params.put("pris", burger.getPrice()+"");


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
