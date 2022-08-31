package za.ac.uj.pyshelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText usernameET, passwordET;
    private boolean passwordShowing = false;
    private Button signInBtn;
    private  TextView sigUpBtn, forgetPasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mAuth = FirebaseAuth.getInstance();

        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        signInBtn = findViewById(R.id.signInBtn);
        sigUpBtn = findViewById(R.id.sigUpBtn);
        forgetPasswordBtn = (TextView) findViewById(R.id.forgetPasswordBtn);

        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, ForgetPassword.class));
            }
        });

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //checks if password is showing or not
                if(passwordShowing){
                    passwordShowing = false;

                    passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ic_remove);
                }
                else{
                    passwordShowing = true;

                    passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ic_password_hide);
                }

                //moving the cursor at the end of the text
                passwordET.setSelection(passwordET.length());


            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();


            }
        });

        sigUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
            }
        });



    }

    private void login() {


        String user = usernameET.getText().toString().trim();
        String pass = passwordET.getText().toString().trim();
        if(user.isEmpty()){
            usernameET.setError("Email can not be empty");
        }
        if(pass.isEmpty()) {
            passwordET.setError("Password can not be empty");
        }
        else
        {
            mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(LoginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginScreen.this , Dashboard.class));
                    }
                    else
                    {
                        Toast.makeText(LoginScreen.this, "Login Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}