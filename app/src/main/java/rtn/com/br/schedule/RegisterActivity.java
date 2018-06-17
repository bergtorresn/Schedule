package rtn.com.br.schedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import rtn.com.br.schedule.firebase.FirebaseService;

/**
 * Created by bergtorres on 17/06/2018
 */
public class RegisterActivity extends AppCompatActivity {

    Button buttonSend;
    EditText editTextEmail;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        configUIElements();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseService.createUser(editTextEmail.getText().toString(),
                                editTextPassword.getText().toString(), RegisterActivity.this);

            }
        });
    }

    private void configUIElements(){
        buttonSend = findViewById(R.id.btn_register_send);
        editTextEmail = findViewById(R.id.editText_email_register);
        editTextPassword = findViewById(R.id.editText_pswrd_register);
    }
}
