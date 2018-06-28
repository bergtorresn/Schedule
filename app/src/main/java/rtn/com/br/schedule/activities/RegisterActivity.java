package rtn.com.br.schedule.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.helpers.CallbackAuthResult;

/**
 * Created by bergtorres on 17/06/2018
 */
public class RegisterActivity extends AppCompatActivity {

    private Button mButtonSend;
    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEditTextName = findViewById(R.id.register_editTextName);
        mEditTextEmail = findViewById(R.id.register_editTextEmail);
        mEditTextPassword = findViewById(R.id.register_editTextPassword);
        mButtonSend = findViewById(R.id.register_buttonSend);

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = mEditTextName.getText().toString();
                String email = mEditTextEmail.getText().toString();
                String password = mEditTextPassword.getText().toString();
                if (!name.isEmpty()){
                    if (!email.isEmpty()){
                        if (!password.isEmpty()){
                            FirebaseService.createAuthUser(email,
                                    password,
                                    RegisterActivity.this,
                                    new CallbackAuthResult() {
                                @Override
                                public void onCallback(Task<AuthResult> resultTask) {
                                    if (resultTask.isSuccessful()) {
                                        Log.i("AUTH", "SUCCESS - CREATE USER");
                                        startActivity(new Intent(RegisterActivity.this, TaskListActivity.class));
                                        finish();
                                        FirebaseService.updateAuthUser(name);
                                    } else {
                                        Log.i("AUTH", "ERROR - CREATE USER " + resultTask.getException().getMessage());
                                        Alerts.genericAlert("ERROR", resultTask.getException().getMessage(), RegisterActivity.this);
                                    }
                                }
                            });
                        }else {
                            Alerts.genericAlert("ERROR", "Senha inválido", RegisterActivity.this);
                        }
                    } else{
                        Alerts.genericAlert("ERROR", "Email inválido", RegisterActivity.this);
                    }
                } else{
                    Alerts.genericAlert("ERROR", "Nome inválido", RegisterActivity.this);
                }
            }
        });
    }
}
