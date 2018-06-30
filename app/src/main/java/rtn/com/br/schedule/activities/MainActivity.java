package rtn.com.br.schedule.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.fragments.UserTaskListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null){
            showFragmentTaskList();
        }
    }

    private void showFragmentTaskList(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.tasklist2_framecontent, new UserTaskListFragment())
                .commit();
    }
}
