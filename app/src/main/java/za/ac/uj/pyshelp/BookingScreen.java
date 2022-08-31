package za.ac.uj.pyshelp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class BookingScreen extends AppCompatActivity  implements View.OnClickListener {

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private TextView selectDate;
    private Button mConfirm;
    private  int flagChecked=0;

    private String date, time = "";

    private CardView c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15;
    private DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference().child("Appointment");
    private DatabaseReference mStudentDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        mConfirm = (Button) findViewById(R.id.confirm_appointment);

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(flagChecked!=0)
                {
                    mDataBaseRef.child(getIntent().getStringExtra("CampusID")).child(date).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int i = 1;
                            for(i=1;i<=15;i++)
                            {
                                if(dataSnapshot.hasChild(String.valueOf(i)))
                                {
                                    if(dataSnapshot.child(String.valueOf(i)).child("StudentID").getValue().toString().equals(mAuth.getCurrentUser().getUid()))
                                    {
                                        Toast.makeText(BookingScreen.this, "You Have Already An Appointment ", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
                            if(i>15)
                            {
                                setTime(flagChecked);
                                mDataBaseRef.child(getIntent().getStringExtra("CampusID")).child(date).child(String.valueOf(flagChecked)).child("StudentID").setValue(mAuth.getCurrentUser().getUid().toString());
                                mStudentDatabase.child("Campus").child(getIntent().getStringExtra("CampusID")).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //String campusName = dataSnapshot.child("name").getValue().toString();

                                        HashMap<String,String> details = new HashMap<>();
                                        details.put("Campus_ID",getIntent().getStringExtra("CampusID"));
                                        details.put("Date",date);
                                        details.put("Time",time);

                                        mStudentDatabase.child("Booked_Appointments").child(mAuth.getCurrentUser().getUid()).push().setValue(details);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                                startActivity(new Intent(BookingScreen.this, ViewBookedAppointment.class));

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
                else{
                    Toast.makeText(BookingScreen.this, "Please Select Time Slot", Toast.LENGTH_SHORT).show();
                }





            }
        });

        c1 = (CardView) findViewById(R.id.time1);
        c2 = (CardView) findViewById(R.id.time2);
        c3 = (CardView) findViewById(R.id.time3);
        c4 = (CardView) findViewById(R.id.time4);
        c5 = (CardView) findViewById(R.id.time5);
        c6 = (CardView) findViewById(R.id.time6);
        c7 = (CardView) findViewById(R.id.time7);
        c8 = (CardView) findViewById(R.id.time8);
        c9 = (CardView) findViewById(R.id.time9);
        c10 = (CardView) findViewById(R.id.time10);
        c11 = (CardView) findViewById(R.id.time11);
        c12 = (CardView) findViewById(R.id.time12);
        c13 = (CardView) findViewById(R.id.time13);
        c14 = (CardView) findViewById(R.id.time14);
        c15 = (CardView) findViewById(R.id.time15);

        selectDate = (TextView) findViewById(R.id.bookAppointment_selectDate);

        date = getIntent().getStringExtra("Date").toString();
        selectDate.setText(date);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(BookingScreen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        date = dayOfMonth +"-"+ (month+1) +"-"+ year;
                        // Toast.makeText(Patient_BookAppointmentActivity.this, date , Toast.LENGTH_SHORT).show();
                        selectDate.setText(date);
                        onStart();


                    }
                },day,month,year);
                datePickerDialog.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + (3 * 60 * 60 * 1000));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000));
                datePickerDialog.show();
            }
        });

    }

    private void setTime(int i) {

        switch (i) {
            case 1:
                time = "08:00 AM";
                break;
            case 2:
                time = "08:20 AM";
                break;
            case 3:
                time = "08:40 AM";
                break;
            case 4:
                time = "09:00 AM";
                break;
            case 5:
                time = "09:20 AM";
                break;
            case 6:
                time = "09:40 AM";
                break;
            case 7:
                time = "10:00 AM";
                break;
            case 8:
                time = "10:20 AM";
                break;
            case 9:
                time = "10:40 AM";
                break;
            case 10:
                time = "11:00 AM";
                break;
            case 11:
                time = "11:20 AM";
                break;
            case 12:
                time = "11:40 AM";
                break;
            case 13:
                time = "02:00 PM";
                break;
            case 14:
                time = "02:20 PM";
                break;
            case 15:
                time = "02:40 PM";
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.time1:
                checkIsBooked(1);
                break;
            case R.id.time2:
                checkIsBooked(2);
                break;
            case R.id.time3:
                checkIsBooked(3);
                break;
            case R.id.time4:
                checkIsBooked(4);
                break;
            case R.id.time5:
                checkIsBooked(5);
                break;
            case R.id.time6:
                checkIsBooked(6);
                break;
            case R.id.time7:
                checkIsBooked(7);
                break;
            case R.id.time8:
                checkIsBooked(8);
                break;
            case R.id.time9:
                checkIsBooked(9);
                break;
            case R.id.time10:
                checkIsBooked(10);
                break;
            case R.id.time11:
                checkIsBooked(11);
                break;
            case R.id.time12:
                checkIsBooked(12);
                break;
            case R.id.time13:
                checkIsBooked(13);
                break;
            case R.id.time14:
                checkIsBooked(14);
                break;
            case R.id.time15:
                checkIsBooked(15);
                break;

            default:
                break;
        }
    }

    private void checkIsBooked(int i) {


        if(flagChecked!=0) {
            setDefaultColor(flagChecked);
            flagChecked = i;
            setColorGreen(i);
        }
        else {
            flagChecked=i;
            setColorGreen(i);
        }


    }

    private void setDefaultColor(int i) {

        switch (i) {
            case 1: c1.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c1.setEnabled(true);
                break;
            case 2:
                c2.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c2.setEnabled(true);
                break;
            case 3:
                c3.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c3.setEnabled(true);
                break;
            case 4:
                c4.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c4.setEnabled(true);
                break;
            case 5:
                c5.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c5.setEnabled(true);
                break;
            case 6:
                c6.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c6.setEnabled(true);
                break;
            case 7:
                c7.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c7.setEnabled(true);
                break;
            case 8:
                c8.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c8.setEnabled(true);
                break;
            case 9:
                c9.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c9.setEnabled(true);
                break;
            case 10:
                c10.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c10.setEnabled(true);
                break;
            case 11:
                c11.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c11.setEnabled(true);
                break;
            case 12:
                c12.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c12.setEnabled(true);
                break;
            case 13:
                c13.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c13.setEnabled(true);
                break;
            case 14:
                c14.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c14.setEnabled(true);
                break;
            case 15:
                c15.setCardBackgroundColor(getResources().getColor(R.color.text_color));
                c15.setEnabled(true);
                break;
            default:
                break;
        }
    }

    private void setColorGreen(int i) {

        switch (i) {
            case 1: c1.setCardBackgroundColor(Color.GREEN);
                break;
            case 2:
                c2.setCardBackgroundColor(Color.GREEN);
                break;
            case 3:
                c3.setCardBackgroundColor(Color.GREEN);
                break;
            case 4:
                c4.setCardBackgroundColor(Color.GREEN);
                break;
            case 5:
                c5.setCardBackgroundColor(Color.GREEN);
                break;
            case 6:
                c6.setCardBackgroundColor(Color.GREEN);
                break;
            case 7:
                c7.setCardBackgroundColor(Color.GREEN);
                break;
            case 8:
                c8.setCardBackgroundColor(Color.GREEN);
                break;
            case 9:
                c9.setCardBackgroundColor(Color.GREEN);
                break;
            case 10:
                c10.setCardBackgroundColor(Color.GREEN);
                break;
            case 11:
                c11.setCardBackgroundColor(Color.GREEN);
                break;
            case 12:
                c12.setCardBackgroundColor(Color.GREEN);
                break;
            case 13:
                c13.setCardBackgroundColor(Color.GREEN);
                break;
            case 14:
                c14.setCardBackgroundColor(Color.GREEN);
                break;
            case 15:
                c15.setCardBackgroundColor(Color.GREEN);
                break;
            default:
                break;
        }
    }

    private void setColorRed(int i) {

        switch (i) {
            case 1: c1.setCardBackgroundColor(Color.RED);
                c1.setEnabled(false);
                break;
            case 2:
                c2.setCardBackgroundColor(Color.RED);
                c2.setEnabled(false);
                break;
            case 3:
                c3.setCardBackgroundColor(Color.RED);
                c3.setEnabled(false);
                break;
            case 4:
                c4.setCardBackgroundColor(Color.RED);
                c4.setEnabled(false);
                break;
            case 5:
                c5.setCardBackgroundColor(Color.RED);
                c5.setEnabled(false);
                break;
            case 6:
                c6.setCardBackgroundColor(Color.RED);
                c6.setEnabled(false);
                break;
            case 7:
                c7.setCardBackgroundColor(Color.RED);
                c7.setEnabled(false);
                break;
            case 8:
                c8.setCardBackgroundColor(Color.RED);
                c8.setEnabled(false);
                break;
            case 9:
                c9.setCardBackgroundColor(Color.RED);
                c9.setEnabled(false);
                break;
            case 10:
                c10.setCardBackgroundColor(Color.RED);
                c10.setEnabled(false);
                break;
            case 11:
                c11.setCardBackgroundColor(Color.RED);
                c11.setEnabled(false);
                break;
            case 12:
                c12.setCardBackgroundColor(Color.RED);
                c12.setEnabled(false);
                break;
            case 13:
                c13.setCardBackgroundColor(Color.RED);
                c13.setEnabled(false);
                break;
            case 14:
                c14.setCardBackgroundColor(Color.RED);
                c14.setEnabled(false);
                break;
            case 15:
                c15.setCardBackgroundColor(Color.RED);
                c15.setEnabled(false);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Toast.makeText(this, "You are not Logged In.....Login first for further process", Toast.LENGTH_SHORT).show();

            Intent login_Intent = new Intent(BookingScreen.this, LoginScreen.class);
            startActivity(login_Intent);
        }else {
            flagChecked=0;
            mDataBaseRef.child(getIntent().getStringExtra("CampusID").toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.hasChild(date)){

                        mDataBaseRef.child(getIntent().getStringExtra("CampusID").toString()).child(date).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (int i =1;i<=15;i++) {

                                    if(dataSnapshot.hasChild(String.valueOf(i)))
                                    {
                                        setColorRed(i);

                                    }
                                    else
                                    {
                                        setDefaultColor(i);
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }else {
                        for (int i =1;i<=15;i++)
                        {
                            setDefaultColor(i);
                        }
                        // Toast.makeText(Patient_BookAppointmentActivity.this, "all time is available on this date", Toast.LENGTH_SHORT).show();
                        // mDataBaseRef.child(CampusID).child(date).child(slot).child("StudentID").setValue(userId);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

}