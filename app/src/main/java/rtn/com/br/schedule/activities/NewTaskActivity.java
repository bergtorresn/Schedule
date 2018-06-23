package rtn.com.br.schedule.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        buttonNewTask = findViewById(R.id.btn_newTask_send);
        editTextName = findViewById(R.id.editText_name_task);
        editTextDescription = findViewById(R.id.editText_description_task);

        buttonNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserTask task = new UserTask();
                task.setTitle(editTextName.getText().toString());
                task.setDescription(editTextDescription.getText().toString());
                task.setCreated_at(new Date());
                task.setPrioridade(1);

                FirebaseService.createUserTask(task, NewTaskActivity.this);
            }
        });
    }
}
