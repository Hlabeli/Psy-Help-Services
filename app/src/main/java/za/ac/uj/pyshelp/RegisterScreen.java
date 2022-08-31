package za.ac.uj.pyshelp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    //Database Reference
    private DatabaseReference mUserDetails = FirebaseDatabase.getInstance().getReference();

    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;
    private EditText email, mobile, password, conPassword, studentName;
    private Button signUpBtn;
    private TextView signInBtn;
    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailET);
        mobile = findViewById(R.id.mobileET);
        studentName = findViewById(R.id.fullName);
        mRegProgress = new ProgressDialog(this);

        password = findViewById(R.id.passwordET);
        EditText conPassword = findViewById(R.id.conPasswordET);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final ImageView conPasswordIcon = findViewById(R.id.conPasswordIcon);

        final AppCompatButton sigUpBtn = findViewById(R.id.signUpBtn);
        final TextView signInBtn = findViewById(R.id.signInBtn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterScreen.this, LoginScreen.class));
            }
        });

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checks if password is showing or not
                if(passwordShowing){
                    passwordShowing = false;

                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ic_remove);
                }
                else{
                    passwordShowing = true;

                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ic_password_hide);
                }

                //moving the cursor at the end of the text
                password.setSelection(password.length());

            }
        });

        conPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checks if password is showing or not
                if(conPasswordShowing){
                    passwordShowing = false;

                    conPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ic_remove);
                }
                else{
                    conPasswordShowing = true;

                    conPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.ic_password_hide);
                }

                //moving the cursor at the end of the text
                conPassword.setSelection(conPassword.length());

            }
        });

        sigUpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String user = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String studName = studentName.getText().toString().trim();
                String studPhone = mobile.getText().toString().trim();

                if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)){

                    mRegProgress.setTitle("Creating Account");
                    mRegProgress.setMessage("Please Wait! We are Processing");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();


                    createAccount(user,pass, studName, studPhone);

                }
                else{

                    Toast.makeText(RegisterScreen.this,"Please fill all field",Toast.LENGTH_LONG).show();

                }

            }

        });


    }

    private void createAccount(final String user, final String pass,final String studName, final String studPhone) {

        mAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = currentUser.getUid();

                    mUserDetails.child("User_Type").child(uid).child("Type").setValue("Student");
                    HashMap<String,String> userDetails = new HashMap<>();
                    userDetails.put("Full Name", studName);
                    userDetails.put("Email",user);
                    userDetails.put("Phone Number", studPhone);
                    userDetails.put("Password",pass);

                    mUserDetails.child("Student_Details").child(uid).setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            mRegProgress.dismiss();

                            Toast.makeText(RegisterScreen.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            verifyEmail(user);

                        }
                    });


                }
                else
                {
                    mRegProgress.hide();
                    Toast.makeText(RegisterScreen.this, "Registration Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }





    private void verifyEmail(final String user) {

        AlertDialog.Builder mBuiler = new AlertDialog.Builder(RegisterScreen.this);
        View mView = getLayoutInflater().inflate(R.layout.verify_email, null);

        TextView userEmail = (TextView) mView.findViewById(R.id.verify_email);
        final TextView sentVerication = (TextView) mView.findViewById(R.id.verify_email_sent);
        Button verifyEmail = (Button) mView.findViewById(R.id.verify_button);
        Button continuebutton = (Button) mView.findViewById(R.id.verify_continue);

        userEmail.setText(user);

        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            sentVerication.setText("We have sent Email to "+user);

                        }
                        else {
                            sentVerication.setText("Failed to Sent Email for Verification");
                        }
                    }
                });
            }
        });

        continuebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_Intent = new Intent(RegisterScreen.this, Dashboard.class);
                startActivity(main_Intent);
            }
        });


        mBuiler.setView(mView);
        AlertDialog dialog = mBuiler.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}