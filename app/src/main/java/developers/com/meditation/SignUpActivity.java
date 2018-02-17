package developers.com.meditation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import developers.com.meditation.Models.User;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        Button signUpToConnectUp = (Button)findViewById(R.id.btnLftArrowSignUp);
        signUpToConnectUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpToConnectUpIntent = new Intent(SignUpActivity.this, ConnectUpActivity.class);
                startActivity(signUpToConnectUpIntent);
            }
        });

    }

    public void callSignUp(View view) {
        User user = new User();
        EditText firstname  = (EditText)findViewById(R.id.firstname);
        EditText lastname = (EditText)findViewById(R.id.lastname);
        EditText emailId = (EditText)findViewById(R.id.emailid);
        EditText phonenumber = (EditText)findViewById(R.id.phonenumber);
        EditText Password = (EditText)findViewById(R.id.password);
        EditText Country = (EditText) findViewById(R.id.country);

        user.setFirstname(firstname.getText().toString());
        user.setLastname(lastname.getText().toString());
        user.setEmailid(emailId.getText().toString());
        user.setPhone_number(phonenumber.getText().toString());
        user.setPassword(Password.getText().toString());
        user.setCountry(Country.getText().toString());

        if( user.getFirstname().isEmpty()|| user.getFirstname() == null || user.getLastname().isEmpty() || user.getLastname() == null || user.getEmailid() == null || user.getEmailid().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty() || user.getCountry().isEmpty() || user.getCountry() == null)
        {
            Toast.makeText(getApplicationContext(),"Please enter all the details ",Toast.LENGTH_LONG).show();
        }
        else {
            callSignUpApi(user);
        }
    }

    public void callSignUpApi(User user)
    {

            if (isNetworkAvailable() != true) {

                Toast.makeText(getApplicationContext(),""+MessageConstant.INTERNET_CONNECTION_ERROR,Toast.LENGTH_LONG).show();

            } else {
             /*   try {
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    String URL = "http://192.168.0.101:3000/register";
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("firstName", firstName);
                    jsonBody.put("lastName", lastName);
                    jsonBody.put("username", emailid);
                    jsonBody.put("password", password);
                    jsonBody.put("country", country);
                    jsonBody.put("contact", phone_number);
                    jsonBody.put("email", emailid);

                    final String mRequestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                                //String error = response;
                                Toast.makeText(getApplicationContext(), "" + MessageConstant.SIGNUP_MESSAGE, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("LOG_VOLLEY", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {

                                responseString = String.valueOf(response.statusCode);

                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    requestQueue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                // Tag used to cancel the request
                // Tag used to cancel the request
                //  String tag_json_obj = "json_obj_req";
                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("firstName", user.getFirstname());
                    jsonBody.put("lastName", user.getLastname());
                    jsonBody.put("username", user.getEmailid());
                    jsonBody.put("password", user.getPassword());
                    jsonBody.put("country", user.getCountry());
                    jsonBody.put("contact", user.getPhone_number());
                    jsonBody.put("email", user.getEmailid());

                    //    final String mRequestBody = jsonBody.toString();

                    String url = "https://meditationnodeapi.herokuapp.com/register";

                    //     ProgressDialog pDialog = new ProgressDialog(this);
                    //     pDialog.setMessage("Loading...");
                    //    pDialog.show();

                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    //  Log.d(TAG, response.toString());

                                    if( response.has("error")) {

                                        Toast.makeText(getApplicationContext(), ""+MessageConstant.DUPLICATE_EMAIL_, Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "" + MessageConstant.SIGNUP_MESSAGE, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d( "Error: " + error.getMessage());
                            //  pDialog.hide();
                        }
                    }) {

                        /**
                         * Passing some request headers
                         */
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json");
                            // headers.put("apiKey", "xxxxxxxxxxxxxxx");
                            return headers;
                        }

                    };

                    // Adding request to request queue
                    requestQueue.add(jsonObjReq);
                    //  VolleySingle.getInstance(this).addToRequestQueue(jsonObjReq);
                } catch (JSONException E) {

                }
            }

    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
