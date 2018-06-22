package android.searchmusic.activity;

import android.os.Bundle;
import android.searchmusic.R;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private String websiteURL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        websiteURL = getIntent().getStringExtra("WebUrl");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = findViewById(R.id.progressBar);

        /*loading web view with in the activity using Url link*/
        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);

            /*Load webview details with checking*/
            if (websiteURL.contains(".")){
                if (websiteURL.startsWith("http://")||websiteURL.startsWith("https://"))
                    webView.loadUrl(websiteURL);
                else if (websiteURL.startsWith("www."))
                    webView.loadUrl("http://"+websiteURL);
                else
                    webView.loadUrl("http://"+websiteURL);
            } else if (websiteURL.startsWith("about:")||websiteURL.startsWith("file:"))
                webView.loadUrl(websiteURL);
            else
                webView.loadUrl("http://www.google.com/#q="+websiteURL);
        }


    //Chrome web view client
    private class myWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                return true;
            default:

                return super.onOptionsItemSelected(item);

        }

    }
}