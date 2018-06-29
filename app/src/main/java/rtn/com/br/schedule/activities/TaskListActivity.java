package rtn.com.br.schedule.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.fragments.TaskListFragment;

public class TaskListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        if (savedInstanceState == null){
            showFragmentTaskList();
        }
    }

    private void showFragmentTaskList(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.tasklist2_framecontent, new TaskListFragment())
                .commit();
    }
}
