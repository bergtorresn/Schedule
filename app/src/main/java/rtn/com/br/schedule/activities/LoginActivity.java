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
import rtn.com.br.schedule.interfaces.CallbackAuthResult;

/**
 * Created by bergtorres on 17/06/2018
 */
public class LoginActivity extends AppCompatActivity {

    // - UI Elements
    private Button mButtonSend;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextEmail = findViewById(R.id.login_editTextEmail);
        mEditTextPassword = findViewById(R.id.login_editTextPassword);
        mButtonSend = findViewById(R.id.login_buttonSend);

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEditTextEmail.getText().toString();
                String password = mEditTextPassword.getText().toString();
                if (!email.isEmpty()) {
                    if (!password.isEmpty()) {
                        FirebaseService.singIn(email,
                                password,
                                LoginActivity.this,
                                new CallbackAuthResult() {
                                    @Override
                                    public void onCallback(Task<AuthResult> resultTask) {
                                        if (resultTask.isSuccessful()) {
                                            Log.i("AUTH", "SUCCESS - LOGIN");
                                            startTaskListActivity();
                                        } else {
                                            Log.i("AUTH", "ERROR - LOGIN " + resultTask.getException().getMessage());
                                            Alerts.genericAlert("ERROR", resultTask.getException().getMessage(), LoginActivity.this);
                                        }
                                    }
                                });
                    } else {
                        Alerts.genericAlert("ERROR", "Senha inválido", LoginActivity.this);
                    }
                } else {
                    Alerts.genericAlert("ERROR", "Email inválido", LoginActivity.this);
                }
            }
        });
    }

    private void startTaskListActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

}
