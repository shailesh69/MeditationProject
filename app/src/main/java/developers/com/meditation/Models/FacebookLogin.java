package developers.com.meditation.Models;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by user on 2/13/2018.
 */

public class FacebookLogin {

    private static String PROTECTED_RESOURCE_URL = "";
    private static String clientId = "";
    private static String clientSecret = "";
    private static String callbackUrl = "";
    //protected static final LogManager LOG = new LogManager(FacebookLogin.class);


    public void getUserData(String accessToken) {
        // SocialLoginDataBean objSocialLoginDataBean = new SocialLoginDataBean();
        // String provider = GenTools.returnEmptyForNull(request.getParameter("provider"));
        // String accessToken = request.getParameter("accessToken");
        OAuth20Service service = null;

        PROTECTED_RESOURCE_URL = "https://graph.facebook.com/v2.8/me?fields=email,"
                + "first_name,last_name,id,name,gender,updated_time,verified";
        service = new ServiceBuilder().apiKey(clientId).apiSecret(clientSecret).callback(callbackUrl)
                .build(FacebookApi.instance());

        final OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL, service);
        service.signRequest(new OAuth2AccessToken(accessToken), oAuthRequest);

//        if (EnvVariables.PROXY_ENABLED) {
//            if (EnvVariables.PROXY_URL != null && EnvVariables.PROXY_PORT != null) {
//                System.getProperties().setProperty("https.proxyHost", EnvVariables.PROXY_URL);
//                System.getProperties().setProperty("https.proxyPort", EnvVariables.PROXY_PORT);
//                System.getProperties().setProperty("http.proxyHost", EnvVariables.PROXY_URL);
//                System.getProperties().setProperty("http.proxyPort", EnvVariables.PROXY_PORT);
//            }
//        }

        try {
            final Response response = oAuthRequest.send();
            String email = getGraphData(response.getBody());

        } catch (IOException e) {
            // LOG.fatal("ERROR in sending oAuthRequest : " + e);
            // objSocialLoginDataBean.setErrorMesage("ERROR in sending oAuthRequest");
        } catch (Exception e) {
            //LOG.fatal("ERROR in sending oAuthRequest : " + e);
            //objSocialLoginDataBean.setErrorMesage("ERROR in sending oAuthRequest");
        }

    }

    public String getGraphData(String socialGraph) {
        //SocialLoginDataBean objSocialLoginDataBean = new SocialLoginDataBean();
        String email="";
        try {
            JSONObject json = new JSONObject(socialGraph);
            String id=json.optString("id");
            String name=json.optString("name");
            email=json.getString("email");

        } catch (JSONException e) {
            //LOG.fatal("ERROR in parsing FB graph data. " + e);
            // objSocialLoginDataBean.setErrorMesage("ERROR in sending oAuthRequest ");
        } catch (Exception e) {
            // LOG.fatal("ERROR in sending oAuthRequest : " + e);
            // objSocialLoginDataBean.setErrorMesage("ERROR in sending oAuthRequest ");
        }
        return email;
    }
}
