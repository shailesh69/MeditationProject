package developers.com.meditation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import developers.com.meditation.Models.User;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    TextView txtStatus;
    private ProfilePictureView profilePictureView;
    private LinearLayout infoLayout;
    private TextView email;
    private TextView gender;
    private TextView facebookName;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_login);

        final EditText emailId = (EditText) findViewById(R.id.txtEmailId);
        final EditText password = (EditText) findViewById(R.id.txtPassword);
        //final TextView textView = (TextView)findViewById(R.id.text);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        emailId.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            boolean hint;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    // no text, hint is visible
                    hint = true;
                    emailId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                    emailId.setTypeface(Typeface.createFromAsset(getAssets(),
                            "hintFont.ttf")); // setting the font
                } else if (hint) {
                    // no hint, text is visible
                    hint = false;
                    emailId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    emailId.setTypeface(Typeface.createFromAsset(getAssets(),
                            "textFont.ttf")); // setting the font
                }
            }

            public void afterTextChanged(Editable s) {}
        });

        Button loginToConnectUp = findViewById(R.id.btnLftArrowLogin);
        loginToConnectUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginToConnectUpIntent = new Intent(LoginActivity.this, ConnectUpActivity.class);
                startActivity(loginToConnectUpIntent);

            }
        });

        initializeControls();

        if( isNetworkAvailable() != true) {
            Toast.makeText(this, ""+MessageConstant.INTERNET_CONNECTION_ERROR, Toast.LENGTH_SHORT).show();

        }else
        {
            loginWithFB();
        }


    }

    private void initializeControls() {
        txtStatus = findViewById(R.id.txtStatusView);
        LoginButton login_button = (LoginButton) findViewById(R.id.login_button);
        login_button.setReadPermissions(Arrays.asList("email", "user_location", "user_hometown"));

        callbackManager = CallbackManager.Factory.create();

    }

    private void loginWithFB() {
        final User user = new User();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //   txtStatus.setText("Login Successful : token - " + loginResult.getAccessToken().getToken() + "uiserid" + loginResult.getAccessToken().getUserId());

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object,GraphResponse response) {
                                        try {
                                            // user.setFirstname(object.getString("first_name"));
                                            // user.setLastname(object.getString("last_name"));
                                            user.setEmailid(object.getString("email"));

                                            String URL = "https://meditationnodeapi.herokuapp.com/search";
                                        }
                                        catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "email,first_name,last_name,id,name,gender,updated_time,verified,user_location,user_hometown");
                        request.setParameters(parameters);

                        //request.setGraphPath(loginResult.getAccessToken().getUserId());
                        //request.setHttpMethod(HttpMethod.GET);

                        //request.setVersion("v2.12");
                        request.executeAsync();


                       /* Bundle parameters = new Bundle();
                        parameters.putString("fields", "email,first_name,last_name,id,name,gender,updated_time,verified");
                        new GraphRequest(
                                loginResult.getAccessToken(),loginResult.getAccessToken().getUserId(),
                                parameters,

                        HttpMethod.GET,
                                new GraphRequest.Callback() {
                                    public void onCompleted(GraphResponse response) {
            *//* handle the result *//*txtStatus.setText("after login"+response.toString());
                                    }
                                }
                        ).executeAsync();
*/
                    }

                    @Override
                    public void onCancel() {
                        txtStatus.setText("Login Cancelled");
                       // Toast.makeText(getApplicationContext(), "Login Cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        txtStatus.setText("Error : " + exception.getCause());
                        exception.printStackTrace();
                        Log.d("error", String.valueOf(exception.getCause()));
                        // App code
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setProfileToView(JSONObject jsonObject) {
        try {
            email.setText(jsonObject.getString("id"));
            gender.setText(jsonObject.getString("gender"));
            facebookName.setText(jsonObject.getString("name"));

            profilePictureView.setPresetSize(ProfilePictureView.NORMAL);
            profilePictureView.setProfileId(jsonObject.getString("id"));
            infoLayout.setVisibility(View.VISIBLE);

            txtStatus.setText((CharSequence) jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void callLoginApi(View v) {
        EditText emailid = (EditText) findViewById(R.id.txtEmailId);
        EditText password = (EditText) findViewById(R.id.txtPassword);

        String emailId = emailid.getText().toString();
        String passWord = password.getText().toString();

        if (emailId.equals("") || passWord.equals("")) {
            Toast.makeText(getApplicationContext(), "" + MessageConstant.EMAIL_PASSWORD_NULL_ERROR, Toast.LENGTH_LONG).show();
        } else {
            if (this.isNetworkAvailable() != true) {

                Toast.makeText(getApplicationContext(), "" + MessageConstant.INTERNET_CONNECTION_ERROR, Toast.LENGTH_LONG).show();

            } else {
                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    String URL = "https://meditationnodeapi.herokuapp.com/login";
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("username", emailId);
                    jsonBody.put("password", passWord);
                    final String mRequestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(), "" + MessageConstant.LOGIN_MESSAGE, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            // String error =

                            Toast.makeText(getApplicationContext(), "" + MessageConstant.INVALID_EMAIL_PASSWORD, Toast.LENGTH_LONG).show();
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
                                //Toast.makeText(getApplicationContext(),"Invalid username or passord",Toast.LENGTH_LONG).show();
                                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                Toast.makeText(getApplicationContext(), "" + MessageConstant.INVALID_EMAIL_PASSWORD, Toast.LENGTH_LONG).show();
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
                }

            }
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void callforgetapi(View v) {
        if (this.isNetworkAvailable() != true) {

            Toast.makeText(getApplicationContext(), "" + MessageConstant.INTERNET_CONNECTION_ERROR, Toast.LENGTH_LONG).show();

        }else
        {
            Intent intent = new Intent(LoginActivity.this,Forgetpassword.class);
            startActivity(intent);

        }
    }
}
