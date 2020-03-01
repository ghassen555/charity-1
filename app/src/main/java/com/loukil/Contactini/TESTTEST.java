package com.loukil.Contactini;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class TESTTEST extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {



private   NavigationView navigationView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testtest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.ShowFirstFragment();

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
        getMenuInflater().inflate(R.menu.testtest, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.idhome) {
            fragment = new fragment_main();

        } else if (id == R.id.deconnexion) {
            FirebaseAuth.getInstance().signOut();
            Intent deconnexion = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(deconnexion);

        } else if (id == R.id.fav) {
            fragment=new fragment_favoris();
        }else if (id == R.id.monprofil) {
            fragment = new fragment_profil();

        }
        else if (id == R.id.contact) {
            fragment = new fragment_Contact();
        }
        if (fragment!=null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screenArea,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

private void ShowFirstFragment()
{
    Fragment visible_fragment = getSupportFragmentManager().findFragmentById(R.id.screenArea);
    if (visible_fragment==null && FirebaseAuth.getInstance().getCurrentUser()!=null )
    {
        Fragment fragment = new fragment_main();

        if (fragment!=null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screenArea,fragment);
            ft.commit();
        }
        navigationView.getMenu().getItem(0).setChecked(true);
    }
    else if (FirebaseAuth.getInstance().getCurrentUser()==null && visible_fragment==null)
    {
        Intent  afterdeconnexion = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(afterdeconnexion);
    }
}


}
