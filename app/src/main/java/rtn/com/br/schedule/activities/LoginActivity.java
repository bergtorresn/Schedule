package rtn.com.br.schedule.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.firebase.FirebaseService;

/**
 * Created by bergtorres on 17/06/2018
 */
public class LoginActivity extends AppCompatActivity {

    private Button buttonSend;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        configUIElements();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseService.singIn(editTextEmail.getText().toString(),
                        editTextPassword.getText().toString(), LoginActivity.this);
                startTaskListActivity();
            }
        });
    }

    private void configUIElements(){
        buttonSend = findViewById(R.id.btn_login_send);
        editTextEmail = findViewById(R.id.editText_email_login);
        editTextPassword = findViewById(R.id.editText_pswrd_login);
    }

    private void startTaskListActivity() {
        Intent intent = new Intent(LoginActivity.this, TaskListActivity.class);
        startActivity(intent);
    }
}
