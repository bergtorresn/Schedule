package rtn.com.br.schedule.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.MyCallback;
import rtn.com.br.schedule.models.UserTask;

public class TaskListActivity extends AppCompatActivity {

    private FloatingActionButton btn_newTask;
    private ListView listview_tasks;
    private List<String> userTasks;
    private ArrayAdapter arrayAdapter;

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

        userTasks = new ArrayList<>();

        fetchUserTasks();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                FirebaseService.singOut();
                startHomeActivity();
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchUserTasks() {
        FirebaseService.getTasks(this, new MyCallback() {
            @Override
            public void onCallback(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserTask userTask = snapshot.getValue(UserTask.class);
                    userTasks.add(userTask.getTitle());
                    arrayAdapter = new ArrayAdapter(TaskListActivity.this, R.layout.list_usertask_layout, userTasks);
                    listview_tasks.setAdapter(arrayAdapter);
                }
            }
        });
    }

    private void startHomeActivity() {
        Intent intent = new Intent(TaskListActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}
