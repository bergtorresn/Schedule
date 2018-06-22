package rtn.com.br.schedule.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import rtn.com.br.schedule.R;

public class TaskListActivity extends AppCompatActivity {

    private FloatingActionButton btn_newTask;
    private ListView listview_tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        btn_newTask = findViewById(R.id.btn_newTask);
        listview_tasks = findViewById(R.id.listview_tasks);

        btn_newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskListActivity.this, NewTaskActivity.class));
            }
        });


    }
}
