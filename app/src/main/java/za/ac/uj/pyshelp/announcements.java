package za.ac.uj.pyshelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class announcements extends AppCompatActivity {

    private ListView listView;
    DatabaseReference databaseReference;
    ArrayList<String> title_list, disc_list, duration_list, venue_list, date_list;
    ArrayAdapter<String> arrayAdapter;
    private SearchView search;
    private LinearLayout layoutBtnBack;
    EventList assist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        listView = findViewById(R.id.listView);

        databaseReference = FirebaseDatabase.getInstance().getReference("eventsApp");
        assist = new EventList();
        title_list = new ArrayList<>();
        disc_list = new ArrayList<>();
        duration_list = new ArrayList<>();
        venue_list = new ArrayList<>();
        date_list = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<>(this,R.layout.item, R.id.item, title_list);

        layoutBtnBack = findViewById(R.id.layoutBtnBack);

        layoutBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(announcements.this, Dashboard.class));
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds:snapshot.getChildren()){
                    assist = ds.getValue(EventList.class);
                    title_list.add(assist.getTitle());
                    date_list.add(assist.getDate());
                    disc_list.add(assist.getDisc());
                    venue_list.add(assist.getVenue());
                    duration_list.add(assist.getDuration());

                }
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(announcements.this, Events.class);
                        String p = date_list.get(position);
                        String a = disc_list.get(position);
                        String b = venue_list.get(position);
                        String d = duration_list.get(position);
                        String r = title_list.get(position);
                        intent.putExtra("title", r);
                        intent.putExtra("date", p);
                        intent.putExtra("disc", a);
                        intent.putExtra("venue", b);
                        intent.putExtra("duration", d);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());

            }
        });


    }
}