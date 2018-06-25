package rtn.com.br.schedule.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private Button buttonSend;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editText_name_register);
        editTextEmail = findViewById(R.id.editText_email_register);
        editTextPassword = findViewById(R.id.editText_pswrd_register);
        buttonSend = findViewById(R.id.btn_register_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextName.getText().toString().isEmpty()){
                    if (!editTextEmail.getText().toString().isEmpty()){
                        if (!editTextPassword.getText().toString().isEmpty()){
                            FirebaseService.createAuthUser(editTextEmail.getText().toString(),
                                    editTextPassword.getText().toString(),
                                    RegisterActivity.this,
                                    new CallbackAuthResult() {
                                @Override
                                public void onCallback(Task<AuthResult> resultTask) {
                                    if (resultTask.isSuccessful()) {
                                        // Log.i("AUTH", "SUCESS CREATE USER");
                                        startActivity(new Intent(RegisterActivity.this, TaskListActivity.class));
                                        FirebaseService.updateAuthUser(editTextName.getText().toString());
                                    } else {
                                        // Log.i("AUTH", "ERROR CREATE USER " + resultTask.getException().getMessage());
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
