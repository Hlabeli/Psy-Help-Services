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

public class Tutorial extends AppCompatActivity {

    private ListView listView;
    DatabaseReference databaseReference;
    ArrayList<String> title_list, answer_list, answer_list2, answer_list3;
    ArrayAdapter<String> arrayAdapter;
    private SearchView search;
    private LinearLayout layoutBtnBack;


    Tutor assist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorials);

        listView = findViewById(R.id.listView);

        databaseReference = FirebaseDatabase.getInstance().getReference("tutorialapp");
        assist = new Tutor();
        title_list = new ArrayList<>();
        answer_list = new ArrayList<>();
        answer_list2 = new ArrayList<>();
        answer_list3 = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<>(this,R.layout.item, R.id.item, title_list);

        layoutBtnBack = findViewById(R.id.layoutBtnBack);

        initSearchWidgets();

        layoutBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Tutorial.this, Dashboard.class));
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds:snapshot.getChildren()){
                    assist = ds.getValue(Tutor.class);
                    title_list.add(assist.getTitle());
                    answer_list.add(assist.getAnswer());
                    answer_list2.add(assist.getAns2());
                    answer_list3.add(assist.getAns3());

                }
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Tutorial.this, Answer.class);
                        String p = answer_list.get(position);
                        String a = answer_list2.get(position);
                        String b = answer_list3.get(position);
                        String r = title_list.get(position);
                        intent.putExtra("title", r);
                        intent.putExtra("answer", p);
                        intent.putExtra("ans2", a);
                        intent.putExtra("ans3", b);
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

    private void initSearchWidgets() {

        search = findViewById(R.id.searchView);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Tutorial.this.arrayAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Tutorial.this.arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }
}