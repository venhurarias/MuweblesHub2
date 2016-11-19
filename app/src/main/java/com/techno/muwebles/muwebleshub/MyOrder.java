package com.techno.muwebles.muwebleshub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FArias on 11/18/2016.
 */

public class MyOrder  extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemClickListener {


    private ArrayList<Product> productList;
    private ListView lvOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);

        HashMap<String, String> postData = new HashMap<String, String>();
        postData.put("email", getIntent().getStringExtra("email"));

        PostResponseAsyncTask taskRead= new PostResponseAsyncTask(this, postData, this);
        taskRead.execute("http://192.168.254.101/webservice/orderbuyer.php");
    }
    @Override
        public void processFinish(String output) {

            productList = new JsonConverter<Product>().toArrayList(output, Product.class);

            BindDictionary<Product> dict = new BindDictionary<Product>();
            dict.addStringField(R.id.ordernumber, new StringExtractor<Product>() {
                @Override
                public String getStringValue(Product product, int position) {
                    return product.id_sale;
                }
            });

            dict.addStringField(R.id.product, new StringExtractor<Product>() {
                @Override
                public String getStringValue(Product product, int position) {
                    return product.product;
                }
            });

            dict.addStringField(R.id.pquantity, new StringExtractor<Product>() {
                @Override
                public String getStringValue(Product product, int position) {
                    return product.number;
                }
            });

        dict.addStringField(R.id.totalprice, new StringExtractor<Product>() {
            @Override
            public String getStringValue(Product product, int position) {
                return product.pricetotal;
            }
        });

            FunDapter<Product> adapter = new FunDapter<>(MyOrder.this, productList, R.layout.table_order, dict);

            lvOrder = (ListView) findViewById(R.id.orderlist);

            lvOrder.setAdapter(adapter);
            lvOrder.setOnItemClickListener(this);







    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Product selectedOrder = productList.get(position);
        Intent in= new Intent(MyOrder.this,DetailActivity.class);


    }
}

