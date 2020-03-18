package com.example.time;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    private FirebaseAuth firebaseAuth;



    private static final String tag="Calender Activity";

    CalendarView calendar ;
    Button event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpToolbar();


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {


            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "/" + "/" + month + 1 + "/" + dayOfMonth;
                Log.d(tag, "onselecteddatechanged date=" + date);
                openDialog();
            }


        });
    }
    public <activity_popup_dialog> void openDialog()
    {
        popup_dialog popup_dialog = new popup_dialog();
        popup_dialog.show(getSupportFragmentManager(),"example");
    }




     /*   navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_syllabus:
                        Intent intent1 = new Intent(home.this, syllabus.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_status:
                        Intent intent2 = new Intent(home.this, status.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_notif:
                        Intent intent3 = new Intent(home.this, schedule.class);
                        startActivity(intent3);
                        break;

                    case R.id.nav_notes:
                        Intent intent4 = new Intent(home.this, notes.class);
                        startActivity(intent4);
                        break;
                    case R.id.nav_logout:
                        Toast.makeText(home.this, "no notifications", Toast.LENGTH_LONG).show();
                        break;

                }
                return false;
            }
        });*/





    /* @Override
     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

     return true;


     }*/
    private void setUpToolbar() {
       try{ drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toorbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();}
       catch(Exception e){};


    }



    @Override


    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {


            case R.id.nav_syllabus:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new syllabus()).commit();
                Intent intent1=new Intent(home.this,syllabus_home.class);
                startActivity(intent1);
                break;

            case R.id.nav_status:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new status()).commit();
                break;

            case R.id.nav_notif:
                //Toast.makeText(home.this, "no notifications", Toast.LENGTH_LONG).show();
                Intent intent2=new Intent(home.this,tasks.class);
                startActivity(intent2);
                break;

            case R.id.nav_notes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new notes()).commit();
                break;

            case R.id.nav_logout:
                Toast.makeText(home.this, "Logged out", Toast.LENGTH_LONG).show();
                firebaseAuth.getInstance().signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(this, MainActivity.class));;
                break;

        }
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }
}


