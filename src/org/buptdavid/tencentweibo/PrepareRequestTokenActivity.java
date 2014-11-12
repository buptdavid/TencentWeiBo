package org.buptdavid.tencentweibo;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrepareRequestTokenActivity extends Activity {

	private OAuthConsumer consumer;
	private OAuthProvider provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		System.setProperty("debug", "true");
		consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY,
				Constants.CONSUMER_SECRET);
		
		provider = new CommonsHttpOAuthProvider(Constants.REQUEST_URL,
				Constants.ACCESS_URL, Constants.AUTHORIZE_URL);
		new OAuthRequestTokenTask(this, consumer, provider).execute();
	}

	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		final Uri uri = intent.getData();
		System.out.println(uri.toString());
		if (uri != null	&& uri.getScheme().equals(Constants.OAUTH_CALLBACK_SCHEME)) {
			new RetrieveAccessTokenTask(this, consumer, provider, prefs).execute(uri);
			finish();
		}
	}
}
