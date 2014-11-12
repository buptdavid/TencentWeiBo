package org.buptdavid.tencentweibo;

import java.util.List;
import java.util.Map;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.buptdavid.tencentweibo.utils.ApacheUtils;
import org.buptdavid.tencentweibo.utils.HttpUtils;
import org.buptdavid.tencentweibo.utils.OAuthUtils;
import org.buptdavid.tencentweibo.utils.StringUtils;
import org.buptdavid.tencentweibo.utils.UrlUtils;

public class WeiBoClient {
	private OAuthConsumer consumer;
	public WeiBoClient(){
		
	}
	
	public WeiBoClient(String consumerKey,String consumerSecret,String oauthToken,String oauthTokenSecret){
		consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
		consumer.setTokenWithSecret(oauthToken, oauthTokenSecret);
	}
	
	public String doGet(String url, Map<String, String> addtionalParams) {
		String result = null;
		url = UrlUtils.buildUrlByQueryStringMapAndBaseUrl(url, addtionalParams);
		String signedUrl = null;
		try {
			System.out.println("before sign URL--->" + url);
			signedUrl = consumer.sign(url);
			System.out.println("After sign URL--->" + signedUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpGet getRequest = new HttpGet(signedUrl);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = null;
		try {
			response = httpClient.execute(getRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = ApacheUtils.parseStringFromEntity(response.getEntity());
		return result;
	}
	
	public String doPost(String url,Map<String,String> addtionalParams,List<String> decodeNames){
		HttpPost postRequest = new HttpPost(url);
		// 签名
		consumer = OAuthUtils.addAddtionalParametersFromMap(consumer, addtionalParams);
		
		try {
			consumer.sign(postRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 发送数据
		Header oauthHeader = postRequest.getFirstHeader("Authorization");
		System.out.println(oauthHeader.getValue());
		String baseString = oauthHeader.getValue().substring(5).trim();
		Map<String,String> oauthMap = StringUtils.parseMapFromString(baseString);
		oauthMap = HttpUtils.decodeByDecodeNames(decodeNames, oauthMap);
		addtionalParams = HttpUtils.decodeByDecodeNames(decodeNames, addtionalParams);
		List<NameValuePair> pairs = ApacheUtils.convertMapToNameValuePairs(oauthMap);
		List<NameValuePair> weiboPairs = ApacheUtils.convertMapToNameValuePairs(addtionalParams);
		pairs.addAll(weiboPairs);
		
		HttpEntity entity = null;
		HttpResponse response = null;
		try {
			entity = new UrlEncodedFormEntity(pairs);
			postRequest.setEntity(entity);
			response = new DefaultHttpClient().execute(postRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String result = ApacheUtils.getResponseText(response); 
		
		return result;
	}
}
