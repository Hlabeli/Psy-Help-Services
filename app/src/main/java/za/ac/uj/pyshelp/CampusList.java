package za.ac.uj.pyshelp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class CampusList extends AppCompatActivity implements RecyclerViewInterface{

    RecyclerView recyclerView, campusList;
    private Button mBookAppointmentBtn;

    DatabaseReference database;
    CampusAdapter campusAdapter;

    ArrayList<Campus> list;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_list);

        recyclerView = findViewById(R.id.campusList);
        database = FirebaseDatabase.getInstance().getReference("Campus");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        campusAdapter = new CampusAdapter(this, list, this);
        recyclerView.setAdapter(campusAdapter);


        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(CampusList.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String userId = getIntent().getStringExtra("UserId");
                        String date = dayOfMonth + "-" + (month + 1) + "-" + year;

                        Intent intent = new Intent(CampusList.this, BookingScreen.class);
                        intent.putExtra("Date", date);
                        intent.putExtra("Campus_ID", userId);
                        startActivity(intent);
                    }
                }, day, month, year);
                datePickerDialog.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + (3 * 60 * 60 * 1000));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000));
                datePickerDialog.show();
            }
        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Campus campus = dataSnapshot.getValue(Campus.class);
                    list.add(campus);
                }
                campusAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(CampusList.this, CampusDetails.class);
        intent.putExtra("name", list.get(position).getName());
        intent.putExtra("abbreviation", list.get(position).getAbbreviation());
        intent.putExtra("location", list.get(position).getLocation());
        intent.putExtra("block", list.get(position).getBlock());
//        intent.putExtra("Toll_free", list.get(position).getToll_free());
        startActivity(intent);



    }
}
