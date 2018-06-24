package rtn.com.br.schedule.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.models.UserTask;

public class TaskDetailActivity extends AppCompatActivity {

    UserTask mUserTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
    }
}
