package com.techno.muwebles.muwebleshub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

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
        taskRead.execute("http://192.168.254.100/webservice/orderbuyer.php");
    }
    @Override
        public void processFinish(String output) {

            productList = new JsonConverter<Product>().toArrayList(output, Product.class);

            BindDictionary<Product> dict = new BindDictionary<Product>();
            dict.addStringField(R.id.idsale, new StringExtractor<Product>() {
                @Override
                public String getStringValue(Product product, int position) {
                    return product.id_sale;
                }
            });

            dict.addStringField(R.id.productname, new StringExtractor<Product>() {
                @Override
                public String getStringValue(Product product, int position) {
                    return product.product;
                }
            });

            dict.addStringField(R.id.orderquantity, new StringExtractor<Product>() {
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
        dict.addStringField(R.id.time, new StringExtractor<Product>() {
            @Override
            public String getStringValue(Product product, int position) {
                return product.time;
            }
        });
        dict.addDynamicImageField(R.id.orderimage, new StringExtractor<Product>() {
            @Override
            public String getStringValue(Product product, int position) {
                return product.path;
            }
        }, new DynamicImageLoader() {
            @Override
            public void loadImage(String url, ImageView imageView) {
                Picasso.with(MyOrder.this)
                        .load(url)
                        .resize(400, 200)
                        .placeholder(android.R.drawable.star_big_on)
                        .error(android.R.drawable.stat_sys_download)
                        .into(imageView);

                // ImageLoader.getInstance().displayImage(url, imageView);

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

