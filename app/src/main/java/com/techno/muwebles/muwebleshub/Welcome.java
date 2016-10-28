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


public class Welcome extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemClickListener {


    RequestQueue requestQueue;
    String rownumberurl = "http://192.168.254.105/webservice/rownumberproduct.php";
    String showUrl = "http://192.168.254.105/webservice/show.php";
    private StringRequest request;
    TextView result;

    private ArrayList<Product> productList;
    private ListView lvProduct;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        result = (TextView) findViewById(R.id.showemail);



        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final String email=getIntent().getStringExtra("email");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageLoader.getInstance().init(UILConfig.config(Welcome.this));

        PostResponseAsyncTask taskRead= new PostResponseAsyncTask(Welcome.this, this);
        taskRead.execute("http://192.168.254.105/webservice/product.php");




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    JSONArray students = response.getJSONArray("list");
                    for (int i = 0; i < students.length(); i++) {
                        JSONObject student = students.getJSONObject(i);
                        if(student.getString("email").equals(email)) {


                            String firstname = student.getString("first");
                            String lastname = student.getString("last");


                            result.append(firstname + " " + lastname + " \n");
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_product:{
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);

                request = new StringRequest(Request.Method.POST, rownumberurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject= new JSONObject(response);


                            final String strrow = jsonObject.getString("rownumber");
                            Intent j = new Intent(getApplicationContext(),NewItem.class);
                            String email=getIntent().getStringExtra("email");
                            j.putExtra("email", email);
                            j.putExtra("rownumber", strrow);
                            startActivity(j);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(request);


                return true;
            }

            case R.id.menu_logout:{
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                return true;
            }


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void processFinish(String s) {
        productList = new JsonConverter<Product>().toArrayList(s, Product.class);

        BindDictionary<Product> dict = new BindDictionary<Product>();
        dict.addStringField(R.id.tvName, new StringExtractor<Product>() {
            @Override
            public String getStringValue(Product product, int position) {
                return product.name;
            }
        });

        dict.addStringField(R.id.tvPrice, new StringExtractor<Product>() {
            @Override
            public String getStringValue(Product product, int position) {
                return product.price;
            }
        });

        dict.addStringField(R.id.tvType, new StringExtractor<Product>() {
            @Override
            public String getStringValue(Product product, int position) {
                return product.type;
            }
        });

        dict.addDynamicImageField(R.id.ivImage, new StringExtractor<Product>() {
            @Override
            public String getStringValue(Product product, int position) {
                return product.path;
            }
        }, new DynamicImageLoader() {
            @Override
            public void loadImage(String url, ImageView imageView) {
                Picasso.with(Welcome.this)
                      .load(url)
                      .resize(400, 400)
                      .placeholder(android.R.drawable.star_big_on)
                      .error(android.R.drawable.stat_sys_download)
                      .into(imageView);

                // ImageLoader.getInstance().displayImage(url, imageView);

            }
        });

        FunDapter<Product> adapter = new FunDapter<>(Welcome.this, productList, R.layout.layout_list, dict);

        lvProduct = (ListView) findViewById(R.id.lvProduct);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemClickListener(this);







    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Product selectedProduct = productList.get(position);
        Intent in= new Intent(Welcome.this,DetailActivity.class);
        in.putExtra("name", selectedProduct.name);
        in.putExtra("type", selectedProduct.type);
        in.putExtra("price", selectedProduct.price);
        in.putExtra("description", selectedProduct.description);
        in.putExtra("image", selectedProduct.path);

        startActivity(in);
    }
}