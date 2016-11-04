package com.techno.muwebles.muwebleshub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by FArias on 11/1/2016.
 */

public class SaleConfirm extends AppCompatActivity  {

    private RequestQueue requestQueue;
    private RequestQueue requestQueue1;
    private Button yes, no;
    private static final String URL = "http://192.168.254.105/webservice/show.php";
    private static final String URL1 = "http://192.168.254.105/webservice/sales.php";
    private StringRequest request;
    private StringRequest request1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sale_confirm);
        yes=(Button) findViewById(R.id.yes);
        no=(Button) findViewById(R.id.no);
        requestQueue = Volley.newRequestQueue(this);
        requestQueue1 = Volley.newRequestQueue(this);

        final String total=getIntent().getStringExtra("total");
        final String quantity=getIntent().getStringExtra("quantity");
        final String item_id=getIntent().getStringExtra("item_id");





        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        URL, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        try {
                            JSONArray students = response.getJSONArray("list");
                            for (int i = 0; i < students.length(); i++) {
                                JSONObject student = students.getJSONObject(i);
                                if(student.getString("email").equals("arias.ven@gmail.com")) {


                                    final String contact = student.getString("contact");
                                    final String email = student.getString("email");
                                    final String address = student.getString("address");
                                    final String first = student.getString("first");
                                    final String last = student.getString("last");
                                    ///hahahhahah
                                    request = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject= new JSONObject(response);
                                                if(jsonObject.names().get(0).equals("Success")){
                                                    Toast.makeText(getApplicationContext(),"SUCCESS "+jsonObject.getString("Success"),Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(SaleConfirm.this,Welcome.class);
                                                    startActivity(i);
                                                }else{
                                                    Toast.makeText(getApplicationContext(),"Error "+jsonObject.getString("Error"),Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }){
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            HashMap<String,String> hashMap = new HashMap<String, String>();

                                            hashMap.put("contact",contact);
                                            hashMap.put("email",email);
                                            hashMap.put("address",address);
                                            hashMap.put("first",first);
                                            hashMap.put("last",last);
                                            hashMap.put("number",quantity);
                                            hashMap.put("pricetotal",total);
                                            return hashMap;
                                        }
                                    };
                                    requestQueue.add(request);


                                    //hahahhahav





                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.append(error.getMessage());

                    }
                });
                requestQueue.add(jsonObjectRequest);



            }


        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });


    }
}