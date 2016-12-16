package com.techno.muwebles.muwebleshub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by FArias on 11/4/2016.
 */

public class QuantityActivity extends AppCompatActivity {
    EditText quantity;
    TextView price, total, itemname, itemprice;
    ImageButton proceed;
    ImageView ivimage;
    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.254.100/webservice/show.php";


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quantity_activity);

        requestQueue = Volley.newRequestQueue(this);
        quantity = (EditText) findViewById(R.id.quantity);
        proceed = (ImageButton) findViewById(R.id.proceed);
        price = (TextView) findViewById(R.id.price);
        total = (TextView) findViewById(R.id.total);
        itemname = (TextView) findViewById(R.id.itemname);
        itemprice = (TextView) findViewById(R.id.itemprice);
        ivimage=(ImageView) findViewById(R.id.ivimage);

        String sprice=getIntent().getStringExtra("price");
        String semail=getIntent().getStringExtra("email");

        itemname.setText(getIntent().getStringExtra("name"));
        itemprice.setText(getIntent().getStringExtra("price"));

        Picasso.with(QuantityActivity.this)
                .load(getIntent().getStringExtra("path"))
                .resize(450, 250)
                .placeholder(android.R.drawable.star_big_on)
                .error(android.R.drawable.stat_sys_download)
                .into(ivimage);

        price.setText(sprice);
        double qnty = Double.valueOf(quantity.getText().toString());
        double prc = Double.valueOf(price.getText().toString());
        double ttl;
        ttl = qnty * prc;
        total.setText(String.valueOf(ttl));






        quantity.addTextChangedListener(new TextWatcher() {

            @Override

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String quantity = s.toString();


                try {
                    double qnty = Double.parseDouble(quantity);
                    double prc = Double.valueOf(price.getText().toString());
                    double ttl;
                    ttl = qnty * prc;

                    total.setText(String.valueOf(ttl));
                } catch (NumberFormatException e) {
                    total.setText("0");

                } catch (Exception e) {

                    total.setText("0");
                }
            }




            @Override

            public void afterTextChanged(Editable s) {

            }

        });
        proceed.setOnClickListener(new View.OnClickListener() {
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
                                if(student.getString("email").equals(getIntent().getStringExtra("email"))) {


                                    final String contact = student.getString("contact");
                                    final String email = student.getString("email");
                                    final String address = student.getString("address");
                                    final String first = student.getString("first");
                                    final String last = student.getString("last");
                                    Intent j = new Intent(getApplicationContext(),SaleConfirm.class);
                                    j.putExtra("total", total.getText().toString());
                                    j.putExtra("quantity", quantity.getText().toString());
                                    j.putExtra("item_id",getIntent().getStringExtra("item_id"));
                                    j.putExtra("semail",getIntent().getStringExtra("semail"));
                                    j.putExtra("path",getIntent().getStringExtra("path"));
                                    j.putExtra("name",getIntent().getStringExtra("name"));
                                    j.putExtra("contact",contact);
                                    j.putExtra("email",email);
                                    j.putExtra("address",address);
                                    j.putExtra("first",first);
                                    j.putExtra("last",last);
                                    startActivity(j);






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
               //

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
}



