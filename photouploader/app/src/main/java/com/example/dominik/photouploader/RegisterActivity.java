package com.example.dominik.photouploader;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends Activity {

    private EditText usern;
    private EditText mail;
    private EditText passw;
    private EditText confpass;
    private String URL= "http://test4apps.keep.pl";
    private String typedMail;
    private String typedPass;
    private String typedConfPass;
    private String typedLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usern = (EditText)findViewById(R.id.regLogin);
        mail = (EditText)findViewById(R.id.regMail);
        passw = (EditText)findViewById(R.id.regPass);
        confpass = (EditText)findViewById(R.id.regConfPass);
    }

    public void onRegisterButtonClick(View view){

        typedLogin = usern.getText().toString();
        typedMail = mail.getText().toString();
        typedPass = passw.getText().toString();
        typedConfPass = confpass.getText().toString();

        if(typedLogin.equals("") || typedMail.equals("") || typedPass.equals("") || typedConfPass.equals("")){
            Toast.makeText(RegisterActivity.this, "All fields must be filled", Toast.LENGTH_LONG).show();
            return;}

        if(typedLogin.length()<=1 || typedMail.length()<=1 || typedPass.length()<=1 || typedConfPass.length()<=1){
            Toast.makeText(RegisterActivity.this, "All fields must be longer than 1 character", Toast.LENGTH_LONG).show();
            return;}

        if(typedPass.equals(typedConfPass)){
            AsyncDataClass2 asyncRequestObject = new AsyncDataClass2();
            asyncRequestObject.execute(URL, typedLogin, typedPass, typedMail);
        }
        else{
            Toast.makeText(RegisterActivity.this,"Passwords must be the same",Toast.LENGTH_LONG).show();
            return;
        }

    }

    private class AsyncDataClass2 extends AsyncTask<String, Void, String> {

        @Override

        protected String doInBackground(String... params) {

            HttpParams httpParameters = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);

            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);

            HttpPost httpPost = new HttpPost(params[0]);

            String jsonResult = "";

            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

                nameValuePairs.add(new BasicNameValuePair("username", params[1]));

                nameValuePairs.add(new BasicNameValuePair("password", params[2]));

                nameValuePairs.add(new BasicNameValuePair("email", params[3]));

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);

                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();

                System.out.println("Returned Json object " + jsonResult.toString());

            } catch (ClientProtocolException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

            return jsonResult;

        }

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            System.out.println("Resulted Value: " + result);

            if(result.equals("") || result == null){

                Toast.makeText(RegisterActivity.this, "Server connection failed", Toast.LENGTH_LONG).show();

                return;

            }

            int jsonResult = returnParsedJsonObject(result);

            if(jsonResult == 0){

                Toast.makeText(RegisterActivity.this, "Invalid mail or password", Toast.LENGTH_LONG).show();

                return;

            }

            if(jsonResult == 1){

                Intent intent = new Intent(RegisterActivity.this, WelcomeActivity.class);

                intent.putExtra("MAIL", typedMail);

                intent.putExtra("MESSAGE", "You have been successfully Registered");

                startActivity(intent);

            }

        }

        private StringBuilder inputStreamToString(InputStream is) {

            String rLine = "";

            StringBuilder answer = new StringBuilder();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            try {

                while ((rLine = br.readLine()) != null) {

                    answer.append(rLine);

                }

            } catch (IOException e) {


                e.printStackTrace();

            }

            return answer;

        }

    }

    private int returnParsedJsonObject(String result){

        JSONObject resultObject = null;

        int returnedResult = 0;

        try {

            resultObject = new JSONObject(result);

            returnedResult = resultObject.getInt("success");

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return returnedResult;

    }

}

