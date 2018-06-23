package rtn.com.br.schedule.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.firebase.FirebaseService;

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


    private void startHomeActivity() {
        Intent intent = new Intent(TaskListActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}
