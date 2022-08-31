package za.ac.uj.pyshelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {
    private LinearLayout layoutTutorial, layoutBook, layoutEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        layoutBook = findViewById(R.id.layoutBook);

        layoutBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, CampusList.class));
            }
        });

        layoutEvents = findViewById((R.id.layoutEvents));

        layoutEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, announcements.class));
            }
        });


        layoutTutorial = findViewById(R.id.layoutTutorial);

        layoutTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Tutorial.class));
            }
        });

        //initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.menuHome);

        //Perform item selected lister
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuHome:
                        return  true;
                    case R.id.menuAppoint:
                        startActivity(new Intent(getApplicationContext(), ViewBookedAppointment.class));
                        overridePendingTransition(0,0);
                        return  true;
                    case R.id.menuContact:
                        startActivity(new Intent(getApplicationContext(), ContactUs.class));
                        overridePendingTransition(0,0);
                        return  true;
                    case R.id.menuLogout:
                        startActivity(new Intent(getApplicationContext(), LoginScreen.class));
                        overridePendingTransition(0,0);
                        return  true;
                }
                return false;
            }
        });
        }
    }
