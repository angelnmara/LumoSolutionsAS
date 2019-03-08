package com.cepadem.lumosolutionsas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import com.cepadem.lumosolutionsas.Fragments.CDEQUERYFragment;
import com.cepadem.lumosolutionsas.Fragments.MyCDEQUERYRecyclerViewAdapter;
import com.cepadem.lumosolutionsas.Fragments.dummy.DummyContent;
import com.cepadem.lumosolutionsas.Models.MenuImg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import com.cepadem.lumosolutionsas.Utils.Utils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //private final String cdequeryfragment = "CDEQUERYFragment";
    Utils utils = new Utils();
    private int mColumnCount = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        try {
            agregaMenu(navigationView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        navigationView.setNavigationItemSelectedListener(this);

        fillListaPrincipal();
        //openFragmentActivos();
    }

    private void fillListaPrincipal() {
        RecyclerView recyclerView = findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, mColumnCount));
        }
        recyclerView.setAdapter(new MyCDEQUERYRecyclerViewAdapter(DummyContent.ITEMS));
    }

    /*private void openFragmentActivos(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = new CDEQUERYFragment();
        transaction.add(R.id.framePrincipal, fragment, cdequeryfragment).addToBackStack(cdequeryfragment).commit();
    }*/

    private void agregaMenu(NavigationView navigationView) throws JSONException {
        /*Obten menu*/
        Menu menu = navigationView.getMenu();
        /*Limpia menu*/
        menu.clear();

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        JSONArray jsaUser = new JSONArray(sharedPreferences.getString(getString(R.string.modulos), ""));
        List<MenuImg> listMenu = utils.GetMenuImg();
        for(int i= 0; i<jsaUser.length(); i++){
            JSONObject jsoUser = jsaUser.getJSONObject(i);
            int resourceIcon = 0;
            for (MenuImg menuImg :listMenu
                 ) {
                if(menuImg.getId() == jsoUser.optInt("MOD_ID")){
                    resourceIcon = menuImg.getImagen();
                }
            }

            if (jsoUser.optInt("MOD_MOD_ID", 0) != 0){
                menu.add(1,jsoUser.optInt("MOD_ORDEN"),jsoUser.optInt("MOD_ORDEN"),jsoUser.optString("MOD_DESCRIPCION")).setIcon(resourceIcon);
            }
        }

        SubMenu subMenu = menu.addSubMenu(2,100,100,"Submenu").setHeaderIcon(R.drawable.side_nav_bar);
        subMenu.add(2,101,101,getString(R.string.comentarios)).setIcon(R.drawable.ic_message_text);
        subMenu.add(2,102,102,getString(R.string.logOut)).setIcon(R.drawable.ic_logout);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if (id == 102){
            logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut(){
        utils.removeShared(this, R.string.token);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    /*@Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Log.d("MainActivity", "onListFragmentInteraction");
    }*/
}
