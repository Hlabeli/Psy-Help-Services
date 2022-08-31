package za.ac.uj.pyshelp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CampusDetails extends AppCompatActivity {

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    TextView camp_details, camp, campus_location, campus_block;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_details);



        camp_details = findViewById(R.id.camp_details);
        camp= findViewById(R.id.camp);
        campus_location = findViewById(R.id.campus_location);
        campus_block = findViewById(R.id.campus_block);


        Button mBookAppointmentBtn = (Button) findViewById(R.id.book_appointment_button);
        mBookAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(CampusDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String name = getIntent().getStringExtra("name");
                        String date = dayOfMonth + "-" + (month + 1) + "-" + year;

                        Intent intent = new Intent(CampusDetails.this, BookingScreen.class);
                        intent.putExtra("Date", date);
                        intent.putExtra("CampusID", name);
                        startActivity(intent);
                    }
                }, day, month, year);
                datePickerDialog.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + (3 * 60 * 60 * 1000));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000));
                datePickerDialog.show();
            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();

        String name = getIntent().getStringExtra("name");
        String location = getIntent().getStringExtra("location");
        String abbreviation = getIntent().getStringExtra("abbreviation");
        String block = getIntent().getStringExtra("block");

        camp_details.setText(name);
        campus_block.setText(block);
        camp.setText(abbreviation);
        campus_location.setText(location);

    }
}