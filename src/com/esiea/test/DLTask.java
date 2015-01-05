package com.esiea.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class DLTask extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... urls){
		try{
			return downloadURL(urls[0]);
		} catch(IOException e){
			return e.toString();
		}
	}

	private String downloadURL(String myUrl) throws IOException{
		InputStream is = null;
		
		try{
			URL url = new URL(myUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			//int response = conn.getResponseCode();
			is = conn.getInputStream();
			
			String contentAsString = readIt(is, 80000);
			return contentAsString;
		} finally{
			if (is != null){
				is.close();
			}
		}
	}
	
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException{
		BufferedReader bReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		StringBuilder strBuilder = new StringBuilder();
		int tmp;
		while((tmp = bReader.read()) != -1){
			strBuilder.append((char) tmp);
		}
		return strBuilder.toString();
	}
	
}

