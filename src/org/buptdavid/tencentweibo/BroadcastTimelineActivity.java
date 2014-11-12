/**
 * 
 */
package org.buptdavid.tencentweibo;

import java.util.HashMap;
import java.util.Map;

import oauth.signpost.OAuth;

import org.buptdavid.tencentweibo.R;
import org.buptdavid.tencentweibo.model.WeiBoList;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;

import com.google.gson.Gson;

/**
 * @author weijielu
 *
 */
public class BroadcastTimelineActivity extends ListActivity {

	private SharedPreferences prefs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        
		Map<String,String> keyValues = new HashMap<String,String>();
		keyValues.put("format", "json");
		keyValues.put("pageflag", "0");
		keyValues.put("pagetime", "0");
		keyValues.put("reqnum", "20");
		String OAuth_token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String OAuth_token_secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
		WeiBoClient weiBoClient = new WeiBoClient(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET, OAuth_token, OAuth_token_secret);
		
		String result = weiBoClient.doGet(Constants.WeiBoApi.HOME_TIMELINE, keyValues);
		Gson gson = new Gson();
		WeiBoList weiBoList = gson.fromJson(result, WeiBoList.class);
		
		WeiBoAdapter weiBoAdapter = new WeiBoAdapter(weiBoList, this);
		ListView listView = getListView();
		listView.setAdapter(weiBoAdapter);
	}
}
