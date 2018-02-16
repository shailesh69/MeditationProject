package developers.com.meditation;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class Forgetpassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        // myWebView.setWebChromeClient(new WebChromeClient());
        // WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        //   webView.setWebViewClient(webViewClient);
        myWebView.loadUrl("https://meditationnodeapi.herokuapp.com/forgot");

        Toast.makeText(getApplicationContext(),"Please perform the action within 60 seconds",Toast.LENGTH_LONG).show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(Forgetpassword.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 60000);

        //   WebSettings webSettings = myWebView.getSettings();
        //    webSettings.setJavaScriptEnabled(true);



        //  webView.loadUrl("https://www.journaldev.com");

    }
}
