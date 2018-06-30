package rtn.com.br.schedule.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.interfaces.CallbackAuthResult;
import rtn.com.br.schedule.interfaces.CallbackDatabase;
import rtn.com.br.schedule.models.User;

/**
 * Created by bergtorres on 17/06/2018
 */
public class RegisterActivity extends AppCompatActivity {

    private Button mButtonSend;
    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEditTextName = findViewById(R.id.register_editTextName);
        mEditTextEmail = findViewById(R.id.register_editTextEmail);
        mEditTextPassword = findViewById(R.id.register_editTextPassword);
        mButtonSend = findViewById(R.id.register_buttonSend);
        mProgressBar = findViewById(R.id.progressBar_register);

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditTextName.getText().toString();
                String email = mEditTextEmail.getText().toString();
                String password = mEditTextPassword.getText().toString();

                if (!name.isEmpty()) {
                    if (!email.isEmpty()) {
                        if (!password.isEmpty()) {
                                showProgressBar();
                            final User user = new User();
                            user.setName(name);
                            user.setEmail(email);

                            FirebaseService.createAuthUser(email, password, RegisterActivity.this, new CallbackAuthResult() {
                                @Override
                                public void onCallback(Task<AuthResult> resultTask) {
                                    if (resultTask.isSuccessful()) {
                                        Log.i("AUTH", "SUCCESS - CREATE USER");
                                        FirebaseService.createUserInDB(user, new CallbackDatabase() {
                                            @Override
                                            public void onCallback(Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    hiddenProgressBar();
                                                    startTaskListActivity();
                                                    Log.d("AUTH", "SUCCESS  - CREATE USER IN DB");
                                                } else {
                                                    hiddenProgressBar();
                                                    Log.d("AUTH", "ERROR - CREATE USER IN DB " + task.getException().getMessage());
                                                }
                                            }
                                        });
                                    } else {
                                        hiddenProgressBar();
                                        Log.i("AUTH", "ERROR - CREATE USER " + resultTask.getException().getMessage());
                                        Alerts.genericAlert("ERROR", resultTask.getException().getMessage(), RegisterActivity.this);
                                    }
                                }
                            });
                        } else {
                            Alerts.genericAlert("ERROR", "Senha inválido", RegisterActivity.this);
                        }
                    } else {
                        Alerts.genericAlert("ERROR", "Email inválido", RegisterActivity.this);
                    }
                } else {
                    Alerts.genericAlert("ERROR", "Nome inválido", RegisterActivity.this);
                }
            }
        });
    }

    private void startTaskListActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setIndeterminate(true);
    }

    private void hiddenProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
        mProgressBar.setIndeterminate(false);
    }
}
