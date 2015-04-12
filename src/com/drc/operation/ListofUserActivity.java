package com.drc.operation;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.drc.operation.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ListofUserActivity extends Activity {
	ListView lstUsername;
	ArrayAdapter<String> userlist ;
	static final String selURL ="http://192.168.0.30/OperationApp/listofuser.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listuser);
		
		lstUsername = (ListView)findViewById(R.id.lstuser);
		userlist = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
	}
	
	public void getdata(){
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(selURL);
			HttpResponse responseuser = httpclient.execute(httppost);
			HttpEntity entity = responseuser.getEntity();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
