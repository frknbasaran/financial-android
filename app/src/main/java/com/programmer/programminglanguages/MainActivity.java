package com.programmer.programminglanguages;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.programmer.programminglanguages.entities.Tech;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String API = "http://188.226.173.227:8080";
     Menu menu;
    NavigationView navigationView;
    List<Tech> techList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


         View hView =  navigationView.getHeaderView(0);

        ImageView imgMain = (ImageView) findViewById(R.id.imgMain);
        Picasso.with(MainActivity.this).load("https://assets-cdn.github.com/images/modules/site/home-ill-platform.png?sn").into(imgMain);

        menu = navigationView.getMenu();



        SubMenu subMenu = menu.addSubMenu("SubMenu Title");



        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Services service =  retrofit.create(Services.class);
        Call call =  service.getAllTech();
        call.enqueue(new Callback<List<Tech>>() {
            @Override
            public void onResponse(Response<List<Tech>> response) {

               techList = response.body();
                if(techList == null){
                    ResponseBody responseBody =  response.errorBody();
                    if(responseBody !=null){
                        Log.e("err",responseBody.toString());
                    }
                }else{
                   for (int i = 1; i < techList.size(); i++) {
                        menu.add(techList.get(i).getName()).setIcon(getResources().getDrawable(R.drawable.ic_menu_send)).setEnabled(true);
                    }



                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        for (int i=0;i<navigationView.getMenu().size();i++){
            if(item==navigationView.getMenu().getItem(i)){
                Log.e("deneme",i+"sldkjnvlksdnc");
                if(techList !=null){
                    Log.e("secilen",techList.get(i).getName());

                    Intent intent = new Intent(this, Detailactivity.class);
                    intent.putExtra("id", techList.get(i).getId());
                    startActivity(intent);



                }
                break;
            }
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
