package rtn.com.br.schedule.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.firebase.FirebaseService;

/**
 * Created by bergtorres on 17/06/2018
 */
public class HomeActivity extends AppCompatActivity {

    // - UI Elements
    private Button mButtonLogin;
    private Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (FirebaseService.checkIfHaveUser()) {
            startTaskListActivity();
        }

        mButtonLogin = findViewById(R.id.home_buttonLogin);
        mButtonRegister = findViewById(R.id.home_buttonRegister);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
            }
        });
    }

    private void startTaskListActivity() {
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        finish();
    }

}
