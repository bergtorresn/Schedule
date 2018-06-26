package rtn.com.br.schedule.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Date;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.models.User;
import rtn.com.br.schedule.models.UserTask;

public class NewTaskActivity extends AppCompatActivity {

    private Button buttonNewTask;
    private EditText editTextName;
    private EditText editTextDescription;
    private RadioGroup radioGroupPriority;
    private RadioButton radioButtonSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        editTextName = findViewById(R.id.editText_name_task);
        editTextDescription = findViewById(R.id.editText_description_task);
        radioGroupPriority = findViewById(R.id.radioGroup_priority);
        buttonNewTask = findViewById(R.id.btn_newTask_send);

        buttonNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int idRadioButtonSelected = radioGroupPriority.getCheckedRadioButtonId();

                if (idRadioButtonSelected > 0) {
                    radioButtonSelected = findViewById(idRadioButtonSelected);
                }

                UserTask task = new UserTask();
                task.setTitle(editTextName.getText().toString());
                task.setDescription(editTextDescription.getText().toString());
                task.setStatus(0);
                taskPriority(task);
                task.setCreated_at(new Date());

                FirebaseService.createUserTask(task, NewTaskActivity.this);
            }
        });
    }

    private void taskPriority(UserTask task){
        switch (radioButtonSelected.getId()) {
            case R.id.radioButton_high:
                task.setPriority(1);
                break;
            case R.id.radioButton_avarage:
                task.setPriority(2);
                break;
            case R.id.radioButton_low:
                task.setPriority(3);
                break;
            default:
                break;
        }
    }
}
