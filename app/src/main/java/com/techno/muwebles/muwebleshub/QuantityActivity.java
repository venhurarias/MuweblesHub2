package com.techno.muwebles.muwebleshub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by FArias on 11/4/2016.
 */

public class QuantityActivity extends AppCompatActivity {
    EditText quantity;
    TextView price, total, squantity;
    Button proceed;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quantity_activity);

        quantity = (EditText) findViewById(R.id.quantity);
        proceed = (Button) findViewById(R.id.proceed);
        price = (TextView) findViewById(R.id.price);
        total = (TextView) findViewById(R.id.total);
        squantity = (TextView) findViewById(R.id.squantity);
        String sprice=getIntent().getStringExtra("price");
        final String item_id=getIntent().getStringExtra("item_id");
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
                    squantity.setText(String.valueOf(qnty));
                    total.setText(String.valueOf(ttl));
                } catch (NumberFormatException e) {
                    total.setText("0");
                    squantity.setText("0");
                } catch (Exception e) {
                    squantity.setText("0");
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
                Intent i = new Intent(getApplicationContext(),SaleConfirm.class);
                i.putExtra("total", total.getText().toString());
                i.putExtra("quantity", squantity.getText().toString());
                i.putExtra("item_id",item_id);
                startActivity(i);

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
}



