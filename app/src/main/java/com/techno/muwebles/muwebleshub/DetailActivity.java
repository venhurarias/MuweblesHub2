package com.techno.muwebles.muwebleshub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView dName, dType, dPrice, dDescription;
    ImageView dImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
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




    }
}
