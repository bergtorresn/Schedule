package rtn.com.br.schedule.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import java.util.Date;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.helpers.CallbackDatabase;
import rtn.com.br.schedule.models.UserTask;

public class NewTaskActivity extends AppCompatActivity {

    private Button mButtonSend;
    private EditText mEditTextName;
    private EditText mEditTextDescription;
    private RadioGroup mRadioGroupPriority;
    private RadioButton mRadioButtonSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        mEditTextName = findViewById(R.id.newtask_editTextTaskName);
        mEditTextDescription = findViewById(R.id.newtask_editTextTaskDescription);
        mRadioGroupPriority = findViewById(R.id.newtask_radioGroupPriority);
        mButtonSend = findViewById(R.id.newtask_buttonSend);

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String taskName = mEditTextName.getText().toString();
                String taskDescription = mEditTextDescription.getText().toString();

                UserTask userTask = new UserTask();

                int idRadioButtonSelected = mRadioGroupPriority.getCheckedRadioButtonId();

                if (idRadioButtonSelected > 0) {
                    mRadioButtonSelected = findViewById(idRadioButtonSelected);
                    if (!taskName.matches("")) {
                        userTask.setTitle(taskName);
                        if (!taskDescription.matches("")) {
                            userTask.setDescription(taskDescription);
                            userTask.setStatus(0);
                            taskPriority(userTask);
                            userTask.setCreated_at(new Date());

                            FirebaseService.createUserTask(userTask, NewTaskActivity.this, new CallbackDatabase() {
                                @Override
                                public void onCallback(Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Log.i("TASK", "SUCCESS CREATE TASK");
                                        Toast.makeText(NewTaskActivity.this, "Tarefa criada com sucesso!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else{
                                        Alerts.genericAlert("Atenção", "Não foi possível criar esta tarefa, tente novamente.", NewTaskActivity.this);
                                    }
                                }
                            });
                        } else {
                            Alerts.genericAlert("Atenção", "Informe uma descrição para esta tarefa.", NewTaskActivity.this);
                        }
                    } else {
                        Alerts.genericAlert("Atenção", "Informe um nome para esta tarefa.", NewTaskActivity.this);
                    }
                } else {
                    Alerts.genericAlert("Atenção", "Selecione uma opção de prioridade para esta tarefa.", NewTaskActivity.this);
                }
            }
        });
    }

    private void taskPriority(UserTask userTask) {
        switch (mRadioButtonSelected.getId()) {
            case R.id.newtask_radioButtonHigh:
                userTask.setPriority(0);
                break;
            case R.id.newtask_radioButtonAvarage:
                userTask.setPriority(1);
                break;
            case R.id.newtask_radioButtonLow:
                userTask.setPriority(2);
                break;
            default:
                break;
        }
    }
}
