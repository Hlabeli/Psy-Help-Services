package za.ac.uj.pyshelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    private LinearLayout layoutLogin, layoutRegister, layoutPassword, layoutContactUs;
    private Button login, register, forgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        layoutLogin = findViewById(R.id.layoutLogin);
        layoutRegister = findViewById(R.id.layoutRegister);
        layoutPassword = findViewById(R.id.layoutPassword);
        layoutContactUs = findViewById(R.id.layoutContactUs);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        forgetPassword = findViewById(R.id.forgetPassword);

        initLoginWidget();
        initRegisterWidget();
        initPasswordWidget();
        initContactWidget();

        initLogin();
        initRegister();
        initPassword();
    }

    private void initPassword() {
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, ForgetPassword.class));
            }
        });

    }

    private void initRegister() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, RegisterScreen.class));
            }
        });
    }

    private void initLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, LoginScreen.class));
            }
        });
    }

    private void initPasswordWidget() {
        layoutPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, ForgetPassword.class));
            }
        });
    }

    private void initRegisterWidget() {
        layoutRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, RegisterScreen.class));
            }
        });
    }

    private void initContactWidget() {
        layoutContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, ContactUs.class));
            }
        });
    }

    private void initLoginWidget() {
        layoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, LoginScreen.class));
            }
        });
    }
}