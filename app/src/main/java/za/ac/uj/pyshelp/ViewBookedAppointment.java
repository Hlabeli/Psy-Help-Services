package za.ac.uj.pyshelp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewBookedAppointment extends AppCompatActivity {

    private RecyclerView recyclerView;
    ImageView imageMenu;

    private String BookedAPKey = "", Appointment_date, slot , Appointment_time , campusID, currentUID;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booked_appointment);

        currentUID = mAuth.getCurrentUser().getUid().toString();

        recyclerView = (RecyclerView) findViewById(R.id.show_Appointment_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageMenu = findViewById(R.id.imageMenu);

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewBookedAppointment.this, Dashboard.class));
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        Query query = mDatabase.child("Booked_Appointments").child(currentUID);

        FirebaseRecyclerOptions<Booked> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Booked>()
                .setQuery(query, Booked.class)
                .build();

        FirebaseRecyclerAdapter<Booked, BookedAppointmentsVH> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Booked, BookedAppointmentsVH>(firebaseRecyclerOptions){

                    @Override
                    public BookedAppointmentsVH onCreateViewHolder(ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.activity_single_booked,parent,false);
                        return new BookedAppointmentsVH(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull final BookedAppointmentsVH holder, @SuppressLint("RecyclerView") final int position, @NonNull final Booked model) {

                        holder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                campusID = model.getCampus_ID();

                                BookedAPKey = getRef(position).getKey().toString();

                                Appointment_date = model.getDate();
                                Appointment_time = model.getTime();
                                changeSlotToTime(Appointment_time);

                                alertDialog();

                            }
                        });
                        mDatabase.child("Campus").child( model.getCampus_ID().toString()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String campNme = dataSnapshot.child("name").getValue(String.class);

                                holder.setCampus_ID(model.getCampus_ID());
                                holder.setDate(model.getDate());
                                holder.setTime(model.getTime());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    private void alertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewBookedAppointment.this);
        builder.setIcon(R.drawable.question).setTitle("Cancel Appointment");
        builder.setMessage("Are You Sure! Want to Cancel Appointment");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mDatabase.child("Appointment").child(campusID).child(Appointment_date).child(slot).removeValue();
                mDatabase.child("Booked_Appointments").child(currentUID).child(BookedAPKey).removeValue();
                onStart();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    public class BookedAppointmentsVH extends RecyclerView.ViewHolder{

        View mView;

        public BookedAppointmentsVH(View itemView) {
            super(itemView);

            mView = itemView;
        }


        public void setCampus_ID(String campusName) {
            TextView name = (TextView) mView.findViewById(R.id.single_campusName);
            name.setText(campusName);
        }



        public void setTime(String time) {

            TextView appointmentTime = (TextView) mView.findViewById(R.id.single_time);
            appointmentTime.setText(time);
        }

        public void setDate(String date) {

            TextView appointmentDate = (TextView) mView.findViewById(R.id.single_date);
            appointmentDate.setText(date);

        }

    }


    private void changeSlotToTime(String appointment_time) {

        switch (appointment_time){

            case "08:00 AM":
                slot = "1";
                break;
            case "08:20 AM":
                slot = "2";
                break;
            case "08:40 AM":
                slot = "3";
                break;
            case "09:00 AM":
                slot = "4";
                break;
            case "09:20 AM":
                slot = "5";
                break;
            case "09:40 AM":
                slot = "6";
                break;
            case "10:00 AM":
                slot = "7";
                break;
            case "10:20 AM":
                slot = "8";
                break;
            case "10:40 AM":
                slot = "9";
                break;
            case "11:00 AM":
                slot = "10";
                break;
            case "11:20 AM":
                slot = "11";
                break;
            case "11:40 AM":
                slot = "12";
                break;
            case "02:00 PM":
                slot = "13";
                break;
            case "02:20 PM":
                slot = "14";
                break;
            case "02:40 PM":
                slot = "15";
                break;
            default:
                break;
        }
    }




}