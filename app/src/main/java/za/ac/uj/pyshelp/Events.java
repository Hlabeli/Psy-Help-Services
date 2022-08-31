package za.ac.uj.pyshelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Events extends AppCompatActivity {

    TextView textView1, textView2, textView3, textView4, textView5;
    ImageView imageMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        textView2 = findViewById(R.id.titleText);
        textView1 = findViewById(R.id.dateText);
        textView3 = findViewById(R.id.venueText);
        textView4 = findViewById(R.id.discText);
        textView5 = findViewById(R.id.durationText);
        textView2.setGravity(Gravity.CENTER_HORIZONTAL);


        String titleTxt = getIntent().getStringExtra("title");
        String answerTxt = getIntent().getStringExtra("date");
        String answerTxt2 = getIntent().getStringExtra("venue");
        String answerTxt3 = getIntent().getStringExtra("disc");
        String answerTxt4 = getIntent().getStringExtra("duration");

        textView2.setText(titleTxt);
        textView1.setText(answerTxt);
        textView3.setText(answerTxt2);
        textView4.setText(answerTxt3);
        textView5.setText(answerTxt4);

        imageMenu = findViewById(R.id.imageMenu);

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Events.this, announcements.class));
            }
        });
    }
}