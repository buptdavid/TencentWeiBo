package org.buptdavid.tencentweibo.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.http.HttpParameters;

public class OAuthUtils {
	
	public static OAuthConsumer addAddtionalParametersFromMap(OAuthConsumer consumer,
			Map<String, String> addtionalParams) {
		Set<String> keys = addtionalParams.keySet();
		Iterator<String> it = keys.iterator();
		
		HttpParameters httpParameters = new HttpParameters();
		while(it.hasNext()){
			String key = it.next();
			String value = addtionalParams.get(key);
			httpParameters.put(key, value);
		}
		consumer.setAdditionalParameters(httpParameters);
		return consumer;
	}
}
