package com.programmer.programminglanguages;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.programmer.programminglanguages.entities.Tech;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Detailactivity extends AppCompatActivity {
    TextView tvTechNameValue ,tvTechDescriptionValue;
    ImageView imgTech;
    String  id;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailactivity);
        tvTechNameValue =  (TextView) findViewById(R.id.tvTechNameValue);
        tvTechDescriptionValue = (TextView) findViewById(R.id.tvTechDescriptionValue);
        imgTech =  (ImageView) findViewById(R.id.imgTech);

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(MainActivity.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        if (getIntent().hasExtra("id")) {
           id = getIntent().getStringExtra("id");
        } else {
            throw new IllegalArgumentException("Activity cannot find  extras  id");
        }
        Services service =  retrofit.create(Services.class);


        Call call =  service.getTech(id);
        call.enqueue(new Callback<Tech>() {
            @Override
            public void onResponse(Response<Tech> response) {

                Tech tech =  response.body();
                Log.e("tech",tech.getName());
                tvTechNameValue.setText(tech.getName());
                tvTechDescriptionValue.setText(tech.getDescription());
                setTitle(tech.getName());
                Picasso.with(Detailactivity.this).load(tech.getImg()).into(imgTech);


            }

            @Override
            public void onFailure(Throwable t) {

                Log.e("hata",t.getMessage());
            }
        });

    }
}
