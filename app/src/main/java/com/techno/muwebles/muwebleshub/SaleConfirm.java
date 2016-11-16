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


                {
                    StringRequest request= new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject= new JSONObject(response);
                                if(jsonObject.names().get(0).equals("success")){
                                    Toast.makeText(getApplicationContext(),"SUCCESS "+jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                }else{
                                    if(jsonObject.names().get(0).equals("error")){
                                        Toast.makeText(getApplicationContext(),"Error "+jsonObject.getString("error"),Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                                    }

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
                            Map<String,String> parameters = new HashMap<String, String>();
                            parameters.put("item_id","asdf");
                            parameters.put("contact",getIntent().getStringExtra("contact"));
                            parameters.put("email","asdf");
                            parameters.put("address","asdf");
                            parameters.put("first","asdf");
                            parameters.put("last","asdf");
                            parameters.put("number",getIntent().getStringExtra("quantity"));
                            parameters.put("pricetotal",getIntent().getStringExtra("total"));
                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                }


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