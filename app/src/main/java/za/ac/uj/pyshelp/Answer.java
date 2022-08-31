package za.ac.uj.pyshelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Answer extends AppCompatActivity {

    TextView textView1, textView2, textView3, textView4;
    ImageView imageMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);


        textView2 = findViewById(R.id.titleText);
        textView1 = findViewById(R.id.answerText);
        textView3 = findViewById(R.id.answer2Text);
        textView4 = findViewById(R.id.answer3Text);
        textView2.setGravity(Gravity.CENTER_HORIZONTAL);


        String titleTxt = getIntent().getStringExtra("title");
        String answerTxt = getIntent().getStringExtra("answer");
        String answerTxt2 = getIntent().getStringExtra("ans2");
        String answerTxt3 = getIntent().getStringExtra("ans3");

        textView2.setText(titleTxt);
        textView1.setText(answerTxt);
        textView3.setText(answerTxt2);
        textView4.setText(answerTxt3);

        imageMenu = findViewById(R.id.imageMenu);

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Answer.this, Tutorial.class));
            }
        });

    }
}