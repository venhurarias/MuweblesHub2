package com.techno.muwebles.muwebleshub;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView dName, dType, dPrice, dDescription;
    ImageView dImage;
    private Button buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        buy=(Button) findViewById(R.id.buy);

        final String name=getIntent().getStringExtra("name");
        final String type=getIntent().getStringExtra("type");
        final String price=getIntent().getStringExtra("price");
        final String description=getIntent().getStringExtra("description");
        final String image=getIntent().getStringExtra("image");

        dName=(TextView)findViewById(R.id.dName);
        dType=(TextView)findViewById(R.id.dType);
        dPrice=(TextView)findViewById(R.id.dPrice);
        dDescription=(TextView)findViewById(R.id.dDescription);
        dImage =(ImageView)findViewById(R.id.dImage);


        dName.setText(name);
        dType.setText(type);
        dPrice.setText(price);
        dDescription.setText(description);
        Picasso.with(DetailActivity.this)
                .load(image)
                .resize(400, 400)
                .placeholder(android.R.drawable.star_big_on)
                .error(android.R.drawable.stat_sys_download)
                .into(dImage);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),QuantityActivity.class);
                i.putExtra("name", dName.getText().toString());
                i.putExtra("price", dPrice.getText().toString());
                i.putExtra("item_id", getIntent().getStringExtra("item_id"));
                i.putExtra("semail", getIntent().getStringExtra("semail"));
                i.putExtra("email", getIntent().getStringExtra("email"));
                i.putExtra("path", image);
                startActivity(i);

            }
        });




    }
}
