package rtn.com.br.schedule.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.adapters.UserTaskAdapter;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.helpers.CallbackDatabase;
import rtn.com.br.schedule.models.UserTask;

public class TaskListActivity extends AppCompatActivity {

    private FloatingActionButton mFloatingActionButton;
    private ListView mListView;
    private List<UserTask> mUserTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        mFloatingActionButton = findViewById(R.id.btn_newTask);
        mListView = findViewById(R.id.listview_tasks);

        mUserTasks = new ArrayList<>();

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskListActivity.this, NewTaskActivity.class));
            }
        });

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
                Alerts.alertSigOut(TaskListActivity.this);
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchUserTasks() {
        FirebaseService.getTasks(this, new CallbackDatabase() {
            @Override
            public void onCallbackDataSnapshot(DataSnapshot dataSnapshot) {
                mUserTasks.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserTask userTask = snapshot.getValue(UserTask.class);
                    mUserTasks.add(userTask);
                    configListView();
                }
            }

            @Override
            public void onCallbackDatabaseError(DatabaseError databaseError) {
                Alerts.genericAlert("Atenção", "Não foi possível se comunicar como servidor, tente novamente.", TaskListActivity.this);
            }
        });
    }

    private void configListView() {

        UserTaskAdapter mUserTaskAdapter = new UserTaskAdapter(mUserTasks, this);
        mListView.setAdapter(mUserTaskAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // UserTask userTask = mUserTasks.get(position);
                Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
