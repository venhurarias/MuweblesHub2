package com.techno.muwebles.muwebleshub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
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

import java.util.ArrayList;
import java.util.HashMap;


public class Welcome extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {


    RequestQueue requestQueue;

    String rownumberurl = "http://192.168.254.100/webservice/rownumberproduct.php";
    String showUrl = "http://192.168.254.100/webservice/show.php";
    String product = "http://192.168.254.100/webservice/search.php";
    private StringRequest request;
    TextView result, newproduct, logout, aboutus, sofa, chair, table, bed, decor;
    private EditText search;
    ImageButton searchb;

    private ArrayList<Product> productList;
    private ListView lvProduct;
    private Spinner typespinner;
    private static final String[]paths = {"","My Orders", "My Product"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        newproduct=(TextView) findViewById(R.id.newproduct);
        logout=(TextView) findViewById(R.id.logout);
        aboutus=(TextView) findViewById(R.id.aboutus);
        sofa=(TextView) findViewById(R.id.sofa);
        chair=(TextView) findViewById(R.id.chair);
        table=(TextView) findViewById(R.id.table);
        bed=(TextView) findViewById(R.id.bed);
        decor=(TextView) findViewById(R.id.decor);
        searchb=(ImageButton) findViewById(R.id.searchb);


        result = (TextView) findViewById(R.id.showemail);
        search = (EditText)findViewById(R.id.search);
        typespinner = (Spinner)findViewById(R.id.menu1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Welcome.this,
                android.R.layout.simple_spinner_item,paths);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typespinner.setAdapter(adapter1);
        typespinner.setOnItemSelectedListener(this);



            product=getIntent().getStringExtra("product");





        newproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AboutActivity.class));

            }
        });




        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final String email=getIntent().getStringExtra("email");


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageLoader.getInstance().init(UILConfig.config(Welcome.this));
        HashMap<String, String> postData = new HashMap<String, String>();
        postData.put("search",getIntent().getStringExtra("search"));

       final PostResponseAsyncTask taskRead= new PostResponseAsyncTask(Welcome.this, postData, this);
        taskRead.execute(product);
        searchb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Welcome.class));
                Intent i = new Intent(Welcome.this,Welcome.class);
                i.putExtra("search",search.getText().toString());
                i.putExtra("product","http://192.168.254.100/webservice/search.php");
                finish();
                startActivity(i);

            }
        });

        sofa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product="http://192.168.254.100/webservice/sofa.php";

                Intent i = new Intent(Welcome.this,Welcome.class);
                i.putExtra("product",product);
                i.putExtra("search",search.getText().toString());
                finish();
                startActivity(i);
            }
        });
        chair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product="http://192.168.254.100/webservice/chair.php";

                Intent i = new Intent(Welcome.this,Welcome.class);
                i.putExtra("product",product);
                i.putExtra("search",search.getText().toString());
                finish();
                startActivity(i);
            }
        });
        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product="http://192.168.254.100/webservice/table.php";

                Intent i = new Intent(Welcome.this,Welcome.class);
                i.putExtra("product",product);
                i.putExtra("search",search.getText().toString());
                finish();
                startActivity(i);
            }
        });
        bed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product="http://192.168.254.100/webservice/bed.php";

                Intent i = new Intent(Welcome.this,Welcome.class);
                i.putExtra("product",product);
                i.putExtra("search",search.getText().toString());
                finish();
                startActivity(i);
            }
        });
        decor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product="http://192.168.254.100/webservice/decor.php";

                Intent i = new Intent(Welcome.this,Welcome.class);
                i.putExtra("product",product);
                i.putExtra("search",search.getText().toString());
                finish();
                startActivity(i);
            }
        });




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



                return true;
            }
            case R.id.my_order:{
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Intent i= new Intent(Welcome.this,MyOrder.class);
                i.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(i);
                return true;
            }
            case R.id.buyerorder:{
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Intent j= new Intent(Welcome.this,BuyerOrder.class);
                j.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(j);


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
       /* dict.addStringField(R.id.tvName, new StringExtractor<Product>() {
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
*/
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
                        .resize(400, 200)
                        .placeholder(android.R.drawable.star_big_on)
                        .error(android.R.drawable.stat_sys_download)
                        .into(imageView);

                // ImageLoader.getInstance().displayImage(url, imageView);

            }
        });

        final FunDapter<Product> adapter = new FunDapter<>(Welcome.this, productList, R.layout.layout_list, dict);



        lvProduct = (ListView) findViewById(R.id.lvProduct);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemClickListener(this);










    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Product selectedProduct = productList.get(position);
        Intent in= new Intent(Welcome.this,DetailActivity.class);

        in.putExtra("item_id", selectedProduct.item_id);
        in.putExtra("name", selectedProduct.name);
        in.putExtra("type", selectedProduct.type);
        in.putExtra("price", selectedProduct.price);
        in.putExtra("description", selectedProduct.description);
        in.putExtra("image", selectedProduct.path);
        in.putExtra("email", getIntent().getStringExtra("email"));
        in.putExtra("semail", selectedProduct.email);

        startActivity(in);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:

                break;
            case 1:
                Intent i = new Intent(getApplicationContext(),MyOrder.class);
                String email=getIntent().getStringExtra("email");
                i.putExtra("email", email);
                startActivity(i);

                break;
            case 2:
                Intent k = new Intent(getApplicationContext(),BuyerOrder.class);
                String selleremail=getIntent().getStringExtra("email");
                k.putExtra("email", selleremail);
                startActivity(k);

                break;


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}




