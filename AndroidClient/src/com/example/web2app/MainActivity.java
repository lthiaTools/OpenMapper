package com.example.web2app;

import android.app.ActionBar;
import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class MainActivity extends Activity {

	private Activity activity;
	private WebView webview;
	private ActionBar actionbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/** 
		 * Display the progress in the activity title bar.
		 */
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		
		setContentView(R.layout.activity_main);
	
		/**
		 * Get actionbar which would have items such as home/forward/back
		 */
		actionbar = getActionBar();
		
		/**
		 * Instantiate a WebView object programmatically
		 * instead of using XML layout.
		 */
		webview = new WebView(this);
		setContentView(webview);

		WebSettings webSettings = webview.getSettings();

		/**
		 * Enable JavaScript functionality in the webview.
		 */
		webSettings.setJavaScriptEnabled(true);

		

		final Activity activity = this;

		/**
		 * WebChromeClient helps to handle Javascript dialogs, 
		 * favicons, titles, and the progress.
		 * We use it for displaying progress
		 */
		webview.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				// Activities and WebViews measure progress with different scales.
				// The progress meter will automatically disappear when we reach 100%
				activity.setProgress(progress * 1000);
			}
		});

		/**
		 * Set the webview client to a customized webview client.
		 * See MyWebViewClient defined below.
		 */
		webview.setWebViewClient( new MyWebViewClient() );

		/**
		 * Load the web site URL specified in values/strings/web_url.
		 */
		webview.loadUrl(getString(R.string.web_url));

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		/*
		 * Handle presses on the action bar items.
		 * Menu items are defined in res/menu/main.xml
		 */
	    switch (item.getItemId()) {
	        case R.id.action_home:
	        	/*
	    		 * Reload the home page
	    		 */
	    		webview.loadUrl(getString(R.string.web_url));
	            return true;
	        case R.id.action_back:
	        	if( webview.canGoBack() ) 
	    		{
	    			webview.goBack();
	    		}
	            return true;
	        case R.id.action_forward:
	        	if( webview.canGoForward() ) 
	    		{
	    			webview.goForward();
	    		}
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/*
	 * Define a customized WebviewClient class 
	 * so that we can override some methods such as authentication request handler.
	 */
	private class MyWebViewClient extends WebViewClient {
		@Override
		public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm)
		{
			/*
			 * We could handler authentication request as follow:
			 * handler.proceed(user_name, password);
			 * 
			 * Notice that the above way is not safe since password 
			 * is revealed in source code.
			 */
			
		}
		
		public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error)
		{	
			handler.proceed();
		}
		
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			Toast.makeText(activity,  description, Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onBackPressed()
	{
		if(webview.canGoBack())
		{
			webview.goBack();
		}
		else
		{
			super.onBackPressed();
		}
	}
}
