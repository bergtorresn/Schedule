package rtn.com.br.schedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.models.Task;
import rtn.com.br.schedule.models.User;

public class NewTaskActivity extends AppCompatActivity {

    private Button buttonNewTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        buttonNewTask = findViewById(R.id.btn_newTask_send);

        buttonNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Task task = new Task();
                task.setTitle("Testar");
                task.setDescription("Testar envio d edados para o database");
                task.setCreated_at(new Date());
                task.setPrioridade("Alta");

                User user = new User();
                List<Task> tasks = new ArrayList<Task>();
                tasks.add(task);
                user.setName("Rosemberg");
                user.setTasks(tasks);


                FirebaseService.createTask(user, NewTaskActivity.this);
            }
        });
    }
}
