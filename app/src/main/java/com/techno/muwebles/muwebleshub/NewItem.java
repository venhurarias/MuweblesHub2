package com.techno.muwebles.muwebleshub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class NewItem extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemSelectedListener {

    public static final String UPLOAD_URL = "http://192.168.254.100/webservice/items.php";
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";
    private EditText name, price, description;
    String type;

    private int PICK_IMAGE_REQUEST = 1;


    private ImageButton buttonChoose;
    private ImageButton buttonUpload;

    private ImageView imageView;
    private Spinner typespinner;
    private static final String[]paths = {"Sofa", "Chair", "Table", "Bed", "Decor"};

    private Bitmap bitmap;

    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newitem_activity);

        buttonChoose = (ImageButton) findViewById(R.id.buttonChoose);
        buttonUpload = (ImageButton) findViewById(R.id.buttonUpload);
        name = (EditText) findViewById(R.id.name);

        price = (EditText) findViewById(R.id.price);
        description = (EditText) findViewById(R.id.description);



        imageView = (ImageView) findViewById(R.id.imageView);

        typespinner = (Spinner)findViewById(R.id.menu);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(NewItem.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typespinner.setAdapter(adapter);
        typespinner.setOnItemSelectedListener(this);




        buttonUpload.setOnClickListener(this);
        buttonChoose.setOnClickListener(this);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String>{
            String Sname= name.getText().toString();
           String Stype= type;
            String Sprice= price.getText().toString();
            String Sdescription= description.getText().toString();
            final String rownumber=getIntent().getStringExtra("rownumber");
            String image =rownumber +".jpg";

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NewItem.this, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);


                HashMap<String,String> data = new HashMap<>();
                data.put("encoded_string", uploadImage);
                data.put("image",image);
                data.put("name",Sname);
                data.put("type",Stype);
                data.put("price",Sprice);
                data.put("description",Sdescription);
                data.put("email",getIntent().getStringExtra("email"));

                String result = rh.sendPostRequest(UPLOAD_URL,data);


                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if(v == buttonUpload){
            uploadImage();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                type="sofa";
                break;
            case 1:
                type="chair";
                break;
            case 2:
                type="table";
                break;
            case 3:
                type="bed";
                break;
            case 4:
                type="decors";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
