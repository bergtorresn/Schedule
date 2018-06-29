package rtn.com.br.schedule.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.UserTaskOutput;
import rtn.com.br.schedule.models.UserTask;

public class TaskDetailActivity extends AppCompatActivity {

    // Propertiers
    private String mStatus[] = {"Não iniciada", "Em andamento", "Cancelada", "Concluída"};
    private Integer mStatusSelected;
    private UserTask mUserTask;

    // UI Elements
    private Spinner mSpinner;
    private Button mBtnUpdateTask;
    private TextView mTaskName;
    private TextView mTaskDescription;
    private TextView mTaskCreatedAt;
    private TextView mTaskPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Intent intent = getIntent();
        mUserTask = (UserTask) intent.getSerializableExtra("UserTask");
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mStatus);

        mTaskName = findViewById(R.id.taskdetail_textViewName);
        mBtnUpdateTask = findViewById(R.id.taskdetail_buttonSend);
        mTaskDescription = findViewById(R.id.taskdetail_textViewDescription);
        mTaskCreatedAt = findViewById(R.id.taskdetail_textViewCreatedAt);
        mTaskPriority = findViewById(R.id.taskdetail_textViewPriority);
        mSpinner = findViewById(R.id.taskdetail_spinnerStatus);

        mTaskName.setText(mUserTask.getName());
        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setSelection(mUserTask.getStatus());
        mTaskCreatedAt.setText(UserTaskOutput.dateOutput(mUserTask.getCreated_at()));
        mTaskPriority.setText(UserTaskOutput.priorityOutput(mUserTask.getPriority()));

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStatusSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBtnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserTask.setStatus(mStatusSelected);
                FirebaseService.editTask(TaskDetailActivity.this, mUserTask);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_removetask, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.taskdetail_buttonMenu_delete:
                alertRemoveTask();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void alertRemoveTask() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Deseja remover essa tarefa?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseService.deleteTask(TaskDetailActivity.this, mUserTask);
                finish();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Alert", "Não");
            }
        });

        builder.setCancelable(false);
        builder.create();
        builder.show();
    }
}
