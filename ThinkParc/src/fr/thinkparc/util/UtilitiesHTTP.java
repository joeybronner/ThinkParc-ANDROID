package fr.thinkparc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class UtilitiesHTTP {

	/* For HTTP Methods */
	public static String convertInputStreamToString(InputStream is) 
			throws IOException {
		BufferedReader bufferedReader;
		bufferedReader = new BufferedReader(new InputStreamReader(is));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null) {
			result += line;
		}

		is.close();
		return result;
	}

	public static String GET(String url){
		String result = "";
		try {
			// Create HttpClient & Perform [GET] request
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			get.setHeader("fct_token", Constants.TOKEN);

			// Handle response
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(get, responseHandler);
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		return result;
	}

	public static String POSTLogin(String url, String u, String p) {
		String res = "";
		try {
			// HttpClient
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			// add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("login", u));
			nameValuePairs.add(new BasicNameValuePair("pass", p));

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// execute HTTP post request
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();

			if (resEntity != null) {
				res = EntityUtils.toString(resEntity).trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return res;
	}
	
	public static String POST(String url) {
		String res = "";
		try {
			// Create HttpClient & Perform [POST] request
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			post.setHeader("fct_token", Constants.TOKEN);

			// Handle response
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(post, responseHandler);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return res;
	}
	
	public static String PUT(String url) {
		try {			
			// Create HttpClient & Perform [PUT] request
			HttpClient httpclient = new DefaultHttpClient();
			HttpPut put = new HttpPut(url);
			put.setHeader("fct_token", Constants.TOKEN);

			// Handle response
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(put, responseHandler);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	/* Check if connection is active or not */
	public static boolean isConnected(ConnectivityManager connManager) {
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) { 
			return true;
		} else {
			return false;
		}
	}
}
