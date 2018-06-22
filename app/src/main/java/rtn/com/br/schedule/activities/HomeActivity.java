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

    private Button buttonLogin;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (FirebaseService.checkIfHaveUser()){
            startTaskListActivity();
        }

        buttonLogin = findViewById(R.id.btn_nav_login);
        buttonRegister = findViewById(R.id.btn_nav_register);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
            }
        });
    }

    private void startTaskListActivity(){
        Intent intent = new Intent(HomeActivity.this, TaskListActivity.class);
        startActivity(intent);
    }
}
