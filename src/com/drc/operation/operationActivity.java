package com.drc.operation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class operationActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    Button btnLogin,btnCancel,btnRegistor;
    EditText edtname,edtpass;
    static final String loginURL = "http://192.168.0.30/OperationApp/logininfo.php";
    InputStream mFile;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnRegistor = (Button)findViewById(R.id.btnRegistor);
        
        btnLogin.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnRegistor.setOnClickListener(this);
        
    }
	public void onClick(View v) {
		// Button Click Event
		if(v == btnLogin){
			getLogindata();
			Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
		}else if(v == btnRegistor) {
			getRegistration();
			Toast.makeText(this, "Registration", Toast.LENGTH_SHORT).show();
		}else {
			finish();
			Toast.makeText(this, "Thanks you for Visti my Application", Toast.LENGTH_SHORT).show();
		}	
	}

	public void getLogindata(){
		
		edtname = (EditText)findViewById(R.id.etxtusername);
		edtpass = (EditText)findViewById(R.id.etxtpassword);
		ArrayList<NameValuePair> namenvaluepair = new ArrayList<NameValuePair>();
		namenvaluepair.add(new BasicNameValuePair("username", edtname.getText().toString()));
		namenvaluepair.add(new BasicNameValuePair("password", edtpass.getText().toString()));
		
		try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(loginURL);
				httppost.setEntity(new UrlEncodedFormEntity(namenvaluepair));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				mFile = entity.getContent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String line = null;
		String result = null;
		
		try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(mFile, "iso-8859-1"),8);
				StringBuilder sb = new StringBuilder();
				while((line=reader.readLine()) != null){
					sb.append(line + "\n");
				}
				mFile.close();
				result = sb.toString();
				System.out.print("Result form WebSite :"+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
				JSONArray jarray = new JSONArray(result);
				for(int i=0; i<jarray.length(); i++){
					JSONObject ob = jarray.getJSONObject(i);
					System.out.println("User Name :"+ob.getString("name"));
					System.out.println("User Name :"+ob.getString("pass"));
					if(jarray.length()>0){
						Intent in = new Intent(this,ListofUserActivity.class);
						startActivity(in);
					}
					else{
						Toast.makeText(this,"Data Not Found Plz Registor Before Login", Toast.LENGTH_SHORT).show();
					}
				}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getRegistration(){
		Intent in = new Intent(this,RegistrationActivity.class);
		startActivity(in);
	}
}
